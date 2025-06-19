package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.LoginActionMapper;
import com.tpp.threat_perception_platform.dao.LoginActionReportMapper;
import com.tpp.threat_perception_platform.dao.LoginLogMapper;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.LoginAction;
import com.tpp.threat_perception_platform.pojo.LoginActionReport;
import com.tpp.threat_perception_platform.pojo.LoginLog;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.LoginActionService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginActionServiceImpl implements LoginActionService {

    @Autowired
    private LoginActionMapper loginActionMapper;

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Autowired
    private LoginActionReportMapper loginActionReportMapper;

    @Override
    public ResponseResult saveLoginAction(LoginAction loginAction) {
        // 先查询是否已存在
        // 可加去重逻辑，比如根据 loginLogId + eventId + timestamp 判重
        LoginLog db = loginActionMapper.selectByloginLogIdAndeventIdAndtimestamp(loginAction.getLoginLogId(),loginAction.getEventId(),loginAction.getTimestamp());
        if (db != null) {
            return new ResponseResult<>(1003, "该记录已存在！");
        }

        // 添加
        loginActionMapper.insert(loginAction);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 展示所有用户的所有action
     * @return
     */
    @Override
    public List<LogParam> getLoginLogsWithActions(){
        List<LoginLog> loginLogs = loginLogMapper.findAll();

        List<LogParam> result = new ArrayList<>();
        for (LoginLog log : loginLogs) {
            List<LoginAction> actions = loginActionMapper.selectByLoginLogId(log.getId());

            LogParam param = new LogParam();
            param.setMac(log.getMac());
            param.setUsername(log.getUsername());
            param.setLoginTime(log.getLoginTime());
            param.setLogoffTime(log.getLogoffTime());
            param.setIsRiskUser(log.getIsRiskUser());
            param.setIsRiskTime(log.getIsRiskTime());
            param.setActions(convertToActionParam(actions)); // 你可能要手动转换 LoginAction → Action

            result.add(param);
        }

        return result;
    }

    private List<LogParam.Action> convertToActionParam(List<LoginAction> actions) {
        List<LogParam.Action> result = new ArrayList<>();
        for (LoginAction act : actions) {
            LogParam.Action action = new LogParam.Action();
            action.setEventId(act.getEventId());
            action.setTimestamp(act.getTimestamp());
            action.setAction(act.getAction());
            action.setDetails(act.getDetails());
            result.add(action);
        }
        return result;
    }

    /**
     * 传入单条用户日志（LogParam），调用大模型进行行为分析，解析结果并存入数据库
     */
    @Override
    public void saveLoginActionReport(LogParam logParam) {
        try {
            // 1. 生成提示词
            String prompt = generatePrompt(logParam);

            // 2. 调用AI，得到生成结果对象
            GenerationResult genResult = AIUtils.callWithMessage(prompt);

            // 3. 从结果中取文本内容
            String aiResponse = genResult.getOutput()
                    .getChoices()
                    .get(0)
                    .getMessage()
                    .getContent();

            // 4. 解析返回的JSON字符串（假设AI返回JSON格式）
            JSONObject json = JSONObject.parseObject(aiResponse);

            // 5. 构造并保存结果
            LoginActionReport analysis = new LoginActionReport();
            analysis.setMac(logParam.getMac());
            analysis.setUsername(logParam.getUsername());
            analysis.setLoginTime(logParam.getLoginTime());
            analysis.setLogoffTime(logParam.getLogoffTime());
            analysis.setRiskLevel(json.getString("risk_level"));
            analysis.setSuspicious(json.getInteger("suspicious"));
            analysis.setReason(json.getString("reason"));
            analysis.setAiPrompt(prompt);
            analysis.setAiRawOutput(aiResponse);

            loginActionReportMapper.insert(analysis);

        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            // 异常处理：日志记录或重试
            e.printStackTrace();
            // 根据业务需要处理异常，比如记录错误、报警等
        }
    }

    /**
     * 查询 AI 用户行为分析结果列表
     * @param param 查询参数（包含分页信息）
     * @return 分页结果
     */
    @Override
    public ResponseResult loginActionReportList(LogParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());

        // 查询所有分析报告
        List<LoginActionReport> reportList = loginActionReportMapper.findAllByMacAndUsernameAndLoginTime(param.getMac(), param.getUsername(),param.getLoginTime());

        // 构建分页信息
        PageInfo<LoginActionReport> pageInfo = new PageInfo<>(reportList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    private String generatePrompt(LogParam logParam) {
        // 根据需要生成提示词，示例保持之前的样式
        StringBuilder sb = new StringBuilder();
        sb.append("你是一名专业的日志数据风险分析师，请根据以下用户行为日志分析是否存在异常或可疑行为，并以 JSON 格式返回结果：\n");
        sb.append(JSONObject.toJSONString(logParam));
        sb.append("\n\n风险评估指南：\n");
        sb.append("1. 整体风险等级：综合考虑操作行为、时间、操作频率等因素，评估为“High（高）”、“Medium（中）”、“Low（低）”或“Safe（安全）”。\n");
        sb.append("   - High：存在多个高风险操作（如权限提升、账号删除），且多发生在非工作时间（凌晨0-6点）或短时间内频繁高危操作。\n");
        sb.append("   - Medium：存在一定可疑行为，但时间相对正常，或频繁但为中等风险操作。\n");
        sb.append("   - Low：操作大多为常规行为，时间正常，无明显异常。\n");
        sb.append("   - Safe：无可疑操作行为。\n");
        sb.append("2. 高风险事件数量：列出高风险事件及其数量。\n");
        sb.append("3. 风险评分：为每个事件打分（0~10）。\n");
        sb.append("4. 操作频率：统计用户操作频率，尤其是高风险操作。\n");
        sb.append("5. 风险描述：总结异常行为的原因，包括时间、频率、操作类型等。\n");
        sb.append("6. 建议措施：根据风险等级提出相应处理建议。\n\n");
        sb.append("请以如下 JSON 格式输出：\n");
        sb.append("{\n");
        sb.append("  \"suspicious\": true/false,\n");
        sb.append("  \"risk_level\": \"High/Medium/Low/Safe\",\n");
        sb.append("  \"reason\": \"简要说明判断依据\"\n");
        sb.append("}");
        return sb.toString();
    }

}
