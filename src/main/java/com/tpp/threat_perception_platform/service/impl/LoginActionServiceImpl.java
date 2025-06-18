package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.fastjson.JSONObject;
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

    private String generatePrompt(LogParam logParam) {
        // 根据需要生成提示词，示例保持之前的样式
        StringBuilder sb = new StringBuilder();
        sb.append("请根据以下用户行为日志分析是否存在异常或可疑行为，并以 JSON 格式返回结果：\n");
        sb.append(JSONObject.toJSONString(logParam));
        sb.append("\n请以如下 JSON 格式输出：\n");
        sb.append("{\n");
        sb.append("  \"suspicious\": true/false,\n");
        sb.append("  \"risk_level\": \"High/Medium/Low/Safe\",\n");
        sb.append("  \"reason\": \"简单描述判断依据\"\n");
        sb.append("}");
        return sb.toString();
    }

}
