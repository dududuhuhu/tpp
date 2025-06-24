package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.ProcessInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ProcessInfoService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ProcessInfoServiceImpl implements ProcessInfoService {

    @Autowired
    private ProcessInfoMapper processInfoMapper;


    @Override
    public int analyzeAndSaveProcessInfo(ProcessInfo processInfo, Date now) {
        // 先查数据库看是否已存在该 mac + pid 记录
        ProcessInfo existing = processInfoMapper.selectByMacAndPid(processInfo.getMac(), processInfo.getPid());

        boolean needAI = true;

        // 设置统一传入的时间
        processInfo.setCollectTime(now);

        if (existing != null) {
            // 判断关键字段是否有变化
            boolean isChanged = false;
            if (!Objects.equals(existing.getPpid(), processInfo.getPpid()) ||
                    !Objects.equals(existing.getName(), processInfo.getName()) ||
                    !Objects.equals(existing.getCmd(), processInfo.getCmd()) ||
                    !Objects.equals(existing.getPriority(), processInfo.getPriority()) ||
                    !Objects.equals(existing.getDescription(), processInfo.getDescription())) {
                isChanged = true;
            }

            if (!isChanged &&
                    existing.getIsHarmful() != null &&
                    existing.getHarmfulKey() != null) {
                // 数据未变化，已有 AI 分析结果，跳过重复调用
                System.out.println("Process info unchanged, skip AI analysis."); // 已存在且无需更新
            }

            processInfo.setId(existing.getId());
            processInfo.setHarmfulKey(existing.getHarmfulKey());
            processInfo.setIsHarmful(existing.getIsHarmful());
            return processInfoMapper.updateByPrimaryKey(processInfo);
        }

        if (needAI) {
            // 拼接提示词
            String promptTemplate =
                    "根据以下进程信息和判断指南，判断进程是否存在安全风险，返回指定JSON格式。\n" +
                            "进程信息：\n" +
                            "MAC地址: %s\n" +
                            "PID: %s\n" +
                            "PPID: %s\n" +
                            "名称: %s\n" +
                            "命令行: %s\n" +
                            "优先级: %s\n" +
                            "描述: %s\n" +
                            "\n" +
                            "判断指南：\n" +
                            "- 如果进程名称为常见系统进程（如system、svchost、explorer、taskmgr等），一般视为无害。\n" +
                            "- 如果进程名称包含已知恶意软件名称或与已知恶意软件行为相似的名称（例如，包含 'Trojan'、'Virus'、'Spyware' 等关键词），则视为有害，风险关键点为'可疑进程名称'。\n" +
                            "- 如果命令行包含可疑参数或路径（如临时目录、未知路径、'Temp'、'AppData\\\\Local\\\\Temp'、'C:\\\\Windows\\\\Temp' 等），视为有害，风险关键点为'可疑命令行'。\n" +
                            "- 如果命令行包含已知的恶意命令或脚本（如 'powershell -exec bypass'、'cmd /c' 等），视为有害，风险关键点为'恶意命令行'。\n" +
                            "- 如果优先级异常高（如超过正常范围，例如高于10），视为有害，风险关键点为'异常优先级'。\n" +
                            "- 如果描述为空或包含可疑内容（如 '无描述'、'未知' 等），视为有害，风险关键点为'可疑描述'。\n" +
                            "- 如果PPID为0且进程名称不是系统进程（如system），视为有害，风险关键点为'可疑父进程'。\n" +
                            "- 其他情况根据具体信息综合判断。\n" +
                            "\n" +
                            "返回格式：\n" +
                            "{\n" +
                            "  \"is_harmful\": 1 或 0,\n" +
                            "  \"harmful_key\": \"风险关键点，无风险则为空\"\n" +
                            "}\n" +
                            "仅返回上述JSON格式，无其他内容。";

            String prompt = String.format(promptTemplate,
                    processInfo.getMac(),
                    processInfo.getPid(),
                    processInfo.getPpid(),
                    processInfo.getName(),
                    processInfo.getCmd(),
                    processInfo.getPriority(),
                    processInfo.getDescription()
            );

            try {
                GenerationResult result = AIUtils.callWithMessage(prompt);
                String content = result.getOutput().getChoices().get(0).getMessage().getContent();

                content = content.replaceAll("(?i)```json", "")
                        .replaceAll("```", "")
                        .trim();

                JSONObject jsonObject = JSON.parseObject(content);
                processInfo.setIsHarmful(jsonObject.getInteger("is_harmful"));
                processInfo.setHarmfulKey(jsonObject.getString("harmful_key"));
            } catch (Exception e) {
                processInfo.setIsHarmful(0);
                processInfo.setHarmfulKey("AI分析失败: " + e.getMessage());
            }
        } else {
            // 保持已有分析结果
            processInfo.setIsHarmful(existing.getIsHarmful());
            processInfo.setHarmfulKey(existing.getHarmfulKey());
        }

        // 插入新数据
        return processInfoMapper.insert(processInfo);
    }

    @Override
    public ResponseResult getByMac(String mac) {
        List<ProcessInfo> processInfos = processInfoMapper.selectByMac(mac);
        if (processInfos == null || processInfos.isEmpty()) {
            return new ResponseResult(404, "未找到进程信息", null);
        }
        return new ResponseResult(200, "成功", processInfos);
    }

    @Override
    public ResponseResult processInfoList(MyParam param) {
        String mac= param.getMacAddress();
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<ProcessInfo> processInfoList = processInfoMapper.selectByMac(mac);
        // 构架pageInfo
        PageInfo<ProcessInfo> pageInfo = new PageInfo<>(processInfoList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}