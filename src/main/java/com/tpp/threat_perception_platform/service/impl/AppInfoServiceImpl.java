package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.AppInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AppInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AppInfoService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private AppInfoMapper appInfoMapper;

    /**
     * 查询 App 列表（分页）
     */
    @Override
    public ResponseResult appList(MyParam param) {
        String mac= param.getMacAddress();
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<AppInfo> appList = appInfoMapper.findAll(mac);
        // 构架pageInfo
        PageInfo<AppInfo> pageInfo = new PageInfo<>(appList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 保存 AppInfo
     */

    @Override
    public int analyzeAndSaveAppInfo(AppInfo appInfo, Date now) {
        // 先查数据库，依据 mac + displayName 唯一定位（可根据业务调整唯一条件）
        AppInfo existing = appInfoMapper.selectByMacAndDisplayName(appInfo.getMac(), appInfo.getDisplayName());

        boolean needAI = true;

        if (existing != null) {
            // 判断关键字段是否变化
            boolean isChanged = false;
            if (!Objects.equals(existing.getInstallLocation(), appInfo.getInstallLocation()) ||
                    !Objects.equals(existing.getUninstallString(), appInfo.getUninstallString())) {
                isChanged = true;
            }

            if (!isChanged &&
                    existing.getIsHarmful() != null &&
                    existing.getHarmfulKey() != null) {
                // 数据未变化，且已有AI分析 → 可跳过AI
                needAI = false;
            }
        }

        if (needAI) {
            // 拼接提示词
            String promptTemplate =
                    "作为专业的网络安全分析师，根据以下应用信息和判断指南，判断应用是否存在安全风险，返回指定JSON格式。\n" +
                            "应用信息：\n" +
                            "MAC地址: %s\n" +
                            "应用名: %s\n" +
                            "安装位置: %s\n" +
                            "卸载指令: %s\n" +
                            "采集时间: %s\n" +
                            "\n" +
                            "判断指南：\n" +
                            "- 如果安装位置包含以下路径，则视为有害，风险关键点为“可疑安装路径”：\n" +
                            "  - 'Temp'\n" +
                            "  - 'AppData\\\\Local\\\\Temp'\n" +
                            "  - 'C:\\\\Users\\\\Public'\n" +
                            "  - 'C:\\\\Windows\\\\Temp'\n" +
                            "- 如果安装位置位于系统关键目录（如 'C:\\\\Windows'、'C:\\\\Program Files'、'C:\\\\Program Files (x86)'）但未通过正规安装程序安装（例如，没有卸载指令或卸载指令指向非法路径），则视为有害，风险关键点为“非法安装行为”。\n" +
                            "- 如果卸载指令为空或为非法路径（例如，指向不存在的文件或目录），则视为有害，风险关键点为“无法卸载风险”。\n" +
                            "- 如果应用名包含已知的恶意软件名称或与已知恶意软件行为相似的名称（例如，包含 'Trojan'、'Virus'、'Spyware' 等关键词），则视为有害，风险关键点为“可疑应用名称”。\n" +
                            "- 如果采集时间与当前时间相差过短（例如，小于1小时），且安装位置为临时文件夹，则视为有害，风险关键点为“临时文件夹中的新安装应用”。\n" +
                            "- 如果应用的MAC地址与已知的恶意设备MAC地址匹配，则视为有害，风险关键点为“已知恶意设备”。\n" +
                            "- 其他情况根据具体信息综合判断。\n" +
                            "\n" +
                            "返回格式：\n" +
                            "{\n" +
                            "  \"is_harmful\": 1 或 0,\n" +
                            "  \"harmful_key\": \"风险关键点，无风险则为空\"\n" +
                            "}\n" +
                            "仅返回上述JSON格式，无其他内容。";

            String prompt = String.format(promptTemplate,
                    appInfo.getMac(),
                    appInfo.getDisplayName(),
                    appInfo.getInstallLocation(),
                    appInfo.getUninstallString(),
                    appInfo.getCollectTime());

            try {
                GenerationResult result = AIUtils.callWithMessage(prompt);
                String content = result.getOutput().getChoices().get(0).getMessage().getContent();

                content = content.replaceAll("(?i)```json", "")
                        .replaceAll("```", "")
                        .trim();

                JSONObject jsonObject = JSON.parseObject(content);
                appInfo.setIsHarmful(jsonObject.getInteger("is_harmful"));
                appInfo.setHarmfulKey(jsonObject.getString("harmful_key"));
            } catch (Exception e) {
                appInfo.setIsHarmful(0);
                appInfo.setHarmfulKey("AI分析失败: " + e.getMessage());
            }
        } else {
            // 保持已有分析结果
            appInfo.setIsHarmful(existing.getIsHarmful());
            appInfo.setHarmfulKey(existing.getHarmfulKey());
        }

        // 设置统一传入的时间
        appInfo.setCollectTime(now);

        if (existing != null) {
            appInfo.setId(existing.getId());
            return appInfoMapper.updateByPrimaryKey(appInfo);
        } else {
            return appInfoMapper.insert(appInfo);
        }
    }

    /**
     * 更新 AppInfo
     */
    @Override
    public ResponseResult editApp(AppInfo appInfo) {
        appInfoMapper.updateByPrimaryKeySelective(appInfo);
        return new ResponseResult<>(0, "更新成功！");
    }

    /**
     * 删除 AppInfo
     */
//    @Override
//    public ResponseResult deleteApp(Integer[] ids) {
//        appInfoMapper.deleteApp(ids);
//        return new ResponseResult<>(0, "删除成功！");
//    }
}