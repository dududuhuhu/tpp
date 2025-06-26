package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.ServiceInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.pojo.ServiceInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ServiceInfoService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class ServiceInfoServiceImpl implements ServiceInfoService {

    @Autowired
    private ServiceInfoMapper serviceInfoMapper;
    @Autowired
    private HostMapper hostMapper;

    @Override
    public int analyzeAndSaveServiceInfo(ServiceInfo serviceInfo, Date now) {
        // 1️⃣ 补全 hostId（通过 mac 查找）
        if (serviceInfo.getHostId() == null) {
            String mac = serviceInfo.getMac();
            if (mac != null) {
                Host host = hostMapper.selectByMacAddress(mac);
                if (host != null) {
                    serviceInfo.setHostId(host.getId());
                } else {
                    throw new RuntimeException("无法找到对应的 host，mac=" + mac);
                }
            } else {
                throw new RuntimeException("serviceInfo 缺少 mac 地址，无法关联 host");
            }
        }

        System.out.println("当前 ServiceInfo: " + JSON.toJSONString(serviceInfo));

        // 2️⃣ 查询是否已有该服务记录
        ServiceInfo existing = serviceInfoMapper.selectByHostIdAndPortAndProtocol(
                serviceInfo.getHostId(),
                serviceInfo.getPort(),
                serviceInfo.getProtocol()
        );

        boolean needAiAnalysis = false;

        serviceInfo.setDetectTime(now);

        if (existing != null) {
            // 判断是否变化
            boolean isChanged = !Objects.equals(existing.getName(), serviceInfo.getName()) ||
                    !Objects.equals(existing.getState(), serviceInfo.getState()) ||
                    !Objects.equals(existing.getProtocol(), serviceInfo.getProtocol()) ||
                    !Objects.equals(existing.getProduct(), serviceInfo.getProduct()) ||
                    !Objects.equals(existing.getVersion(), serviceInfo.getVersion()) ||
                    !Objects.equals(existing.getExtrainfo(), serviceInfo.getExtrainfo());

            if (isChanged) {
                needAiAnalysis = true;
                serviceInfo.setId(existing.getId());  // 保留主键用于更新
            } else {
                // 数据完全一致，不需分析
                System.out.println("服务信息未变化，跳过 AI 分析。");
            }

            serviceInfo.setId(existing.getId());
            serviceInfo.setHarmfulKey(existing.getHarmfulKey());
            serviceInfo.setIsHarmful(existing.getIsHarmful());
            int updated = serviceInfoMapper.updateByPrimaryKey(serviceInfo);
            System.out.println("服务更新结果: " + updated);
            return updated;
        } else {
            // 新数据，需要分析
            needAiAnalysis = true;
        }

        if (needAiAnalysis) {
            // 3️⃣ 拼接提示词
            // 拼接提示词
            // 拼接提示词
            String promptTemplate =
                    "根据以下服务信息和判断指南，判断服务是否存在安全风险，返回指定JSON格式。\n" +
                            "服务信息：\n" +
                            "Host ID: %s\n" +
                            "端口: %s\n" +
                            "名称: %s\n" +
                            "状态: %s\n" +
                            "协议: %s\n" +
                            "产品: %s\n" +
                            "版本: %s\n" +
                            "附加信息: %s\n" +
                            "\n" +
                            "判断指南：\n" +
                            "- 如果服务状态为 'closed'，视为无害。\n" +
                            "- 如果服务状态为 'open' 且端口号为以下高风险端口（21, 23, 135,  445,  3389），视为有害，风险关键点为 '高风险端口暴露'。\n" +
                            "- 如果协议为 FTP 或 Telnet，视为有害，风险关键点为 '不安全协议'。\n" +
                            "- 如果服务状态为 'open' 且产品和版本信息均缺失，视为有害，风险关键点为 '未知服务'。\n" +
                            "- 其他情况视为无害。\n" +
                            "\n" +
                            "返回格式：\n" +
                            "{\n" +
                            "  \"is_harmful\": 1 或 0,\n" +
                            "  \"harmful_key\": \"风险关键点，无风险则为空\"\n" +
                            "}\n" +
                            "仅返回上述JSON格式，无其他内容。";


            String prompt = String.format(promptTemplate,
                    serviceInfo.getHostId(),
                    serviceInfo.getPort(),
                    serviceInfo.getName(),
                    serviceInfo.getState(),
                    serviceInfo.getProtocol(),
                    serviceInfo.getProduct(),
                    serviceInfo.getVersion(),
                    serviceInfo.getExtrainfo()
            );

            System.out.println("准备调用 AI，提示词：\n" + prompt);

            // 4️⃣ 调用 AI
            try {
                GenerationResult result = AIUtils.callWithMessage(prompt);
                String content = result.getOutput().getChoices().get(0).getMessage().getContent();

                content = content.replaceAll("(?i)```json", "")
                        .replaceAll("```", "")
                        .trim();

                System.out.println("AI 返回内容: " + content);

                JSONObject jsonObject = JSON.parseObject(content);
                serviceInfo.setIsHarmful(jsonObject.getInteger("is_harmful"));
                serviceInfo.setHarmfulKey(jsonObject.getString("harmful_key"));
            } catch (Exception e) {
                System.err.println("AI 分析失败: " + e.getMessage());
                serviceInfo.setIsHarmful(0);
                serviceInfo.setHarmfulKey("AI分析失败: " + e.getMessage());
            }
        }

        int inserted = serviceInfoMapper.insert(serviceInfo);
        System.out.println("服务插入结果: " + inserted);
        return inserted;
    }


    @Override
    public ResponseResult selectByHostId(Long hostId) {
        List<com.tpp.threat_perception_platform.pojo.ServiceInfo> serviceList = serviceInfoMapper.selectByHostId(hostId);
        return new ResponseResult<>(0, serviceList);
    }

    @Override
    public ResponseResult retrieveAssetsService(MyParam param) {
        String mac= param.getMacAddress();
        Long id = hostMapper.selectByMacAddress(mac).getId();
        PageHelper.startPage(param.getPage(), param.getLimit());

        // 查询方法会被分页插件拦截
        List<ServiceInfo> serviceList = serviceInfoMapper.selectByHostId(Long.valueOf(id));

        // PageHelper 会自动分页并计算 total
        PageInfo<ServiceInfo> pageInfo = new PageInfo<>(serviceList);

        // 正确返回 total 和当前页数据
        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

}
