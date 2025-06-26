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
    public List<LogParam> getLoginLogsWithActions(LogParam params) {
        System.out.println("audit_params:"+params);
        List<LoginLog> loginLogs = loginLogMapper.findAllForAudit(params);
        List<LogParam> result = new ArrayList<>();

        for (LoginLog log : loginLogs) {
            List<LoginAction> actions = loginActionMapper.selectByLoginLogId(log.getId());

            // ✅ 只保留有 actions 的记录
            if (actions == null || actions.isEmpty()) {
                System.out.println("actions is null or empty");
                continue; // 跳过
            }

            LogParam param = new LogParam();
            param.setMac(log.getMac());
            param.setUsername(log.getUsername());
            param.setLoginTime(log.getLoginTime());
            param.setLogoffTime(log.getLogoffTime());
            param.setIsRiskUser(log.getIsRiskUser());
            param.setIsRiskTime(log.getIsRiskTime());
            param.setActions(convertToActionParam(actions));

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
        // ✅ 先查询是否已有分析报告
        List<LoginActionReport> existingReports = loginActionReportMapper.findAllByMacAndUsernameAndLoginTime(
                logParam.getMac(), logParam.getUsername(), logParam.getLoginTime()
        );
        if (existingReports != null && !existingReports.isEmpty()) {
            // 已有结果，无需重复调用
            System.out.println("已存在审计日志报告！");
            return;
        }

        try {
            // 🧠 生成提示词
            String prompt = generatePrompt(logParam);

            // 🧠 调用大模型生成分析结果
            GenerationResult genResult = AIUtils.callWithMessage(prompt);

            // 🧠 提取 AI 返回内容
            String aiResponse = genResult.getOutput().getChoices().get(0).getMessage().getContent();
            System.out.println("AI返回原始内容:\n" + aiResponse);

            String cleanJson = extractJson(aiResponse);
            JSONObject json = JSONObject.parseObject(cleanJson);

            // ✅ 构造分析报告实体
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

            // ✅ 存入数据库
            loginActionReportMapper.insert(analysis);
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            e.printStackTrace();
            // TODO：可写日志或告警
        }
    }

    private String extractJson(String str) {
        if (str == null) return null;
        str = str.trim();

        // 去除开头的 ```json 或 ```
        if (str.startsWith("```json")) {
            str = str.substring(6).trim();
        } else if (str.startsWith("```")) {
            str = str.substring(3).trim();
        }

        // 去除结尾的 ```
        if (str.endsWith("```")) {
            str = str.substring(0, str.length() - 3).trim();
        }

        // 再提取第一个 { 到最后一个 } 之间的内容，避免其他非 JSON 字符影响解析
        int firstBrace = str.indexOf('{');
        int lastBrace = str.lastIndexOf('}');
        if (firstBrace >= 0 && lastBrace > firstBrace) {
            str = str.substring(firstBrace, lastBrace + 1);
        } else {
            // 如果找不到有效的 JSON 对象，返回空字符串或null，避免异常
            return null;
        }

        return str;
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

        System.out.println("loginparam:"+param);
        // 查询所有分析报告
        List<LoginActionReport> reportList = loginActionReportMapper.findAllByMacAndUsernameAndLoginTime(param.getMac(), param.getUsername(),param.getLoginTime());

        // 构建分页信息
        PageInfo<LoginActionReport> pageInfo = new PageInfo<>(reportList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    private String generatePrompt(LogParam logParam) {
        StringBuilder sb = new StringBuilder();
        sb.append("请根据以下用户行为日志，判断是否存在异常或可疑行为，直接以标准 JSON 格式返回结果，不要添加注释或说明文字。\n");
        sb.append("以下是日志数据：\n");
        sb.append(JSONObject.toJSONString(logParam));
        sb.append("\n请直接以如下格式返回（不包含markdown标记、解释说明或其他文字）：\n");
        sb.append("{\n");
        sb.append("  \"suspicious\": true/false,\n");
        sb.append("  \"risk_level\": \"High/Medium/Low/Safe\",\n");
        sb.append("  \"reason\": \"简要说明判断依据\"\n");
        sb.append("}");
        return sb.toString();
    }


}
