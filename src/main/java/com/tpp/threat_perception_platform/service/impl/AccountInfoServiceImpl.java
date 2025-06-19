package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.AccountInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AccountInfoService;
import com.tpp.threat_perception_platform.utils.AIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {


    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public int analyzeAndSaveAccountInfo(AccountInfo accountInfo) {
        // 拼接提示词（可选拼接更多字段）
        String promptTemplate =
                "根据以下账号信息和判断指南，判断账号是否存在安全风险，返回指定JSON格式。\n" +
                        "账号信息：\n" +
                        "MAC地址: %s\n" +
                        "账号名: %s\n" +
                        "全名: %s\n" +
                        "SID: %s\n" +
                        "SID类型: %s\n" +
                        "状态: %s\n" +
                        "是否禁用: %s\n" +
                        "是否锁定: %s\n" +
                        "密码可更改: %s\n" +
                        "密码过期: %s\n" +
                        "密码必需: %s\n" +
                        "\n" +
                        "判断指南：\n" +
                        "- 如果账号状态为'锁定'或'禁用'，则视为无害。\n" +
                        "- 如果密码过期且密码不可更改，则视为有害，风险关键点为'密码管理不当'。\n" +
                        "- 如果SID类型为'管理员'且密码不必需，则视为有害，风险关键点为'高权限账号风险'。\n" +
                        "- 其他情况根据具体信息综合判断。\n" +
                        "\n" +
                        "返回格式：\n" +
                        "{\n" +
                        "  \"is_harmful\": 1 或 0,\n" +
                        "  \"harmful_key\": \"风险关键点，无风险则为空\"\n" +
                        "}\n" +
                        "仅返回上述JSON格式，无其他内容。";

        String prompt = String.format(promptTemplate,
                accountInfo.getMac(),
                accountInfo.getName(),
                accountInfo.getFullName(),
                accountInfo.getSid(),
                accountInfo.getSidType(),
                accountInfo.getStatus(),
                accountInfo.getDisabled(),
                accountInfo.getLockout(),
                accountInfo.getPasswordChangeable(),
                accountInfo.getPasswordExpires(),
                accountInfo.getPasswordRequired()
        );

        try {
            // 调用 AI 分析
            GenerationResult result = AIUtils.callWithMessage(prompt);
            String content = result.getOutput().getChoices().get(0).getMessage().getContent();

            // 清理可能的多余标记
            content = content.replaceAll("(?i)```json", "")
                    .replaceAll("```", "")
                    .trim();

            // 解析 JSON
            JSONObject jsonObject = JSON.parseObject(content);

            // 设置 AccountInfo 字段
            accountInfo.setIsHarmful(jsonObject.getInteger("is_harmful"));
            accountInfo.setHarmfulKey(jsonObject.getString("harmful_key"));

        } catch (Exception e) {
            // 如果 AI 调用失败，默认安全
            accountInfo.setIsHarmful(0);
            accountInfo.setHarmfulKey("AI分析失败: " + e.getMessage());
        }

        // 设置创建时间、更新时间
        Date now = new Date();
        accountInfo.setCreatedAt(now);
        accountInfo.setUpdatedAt(now);

        // 入库
        return accountInfoMapper.insertSelective(accountInfo);
    }


    @Override
    public ResponseResult accountList(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<AccountInfo> accountList = accountInfoMapper.findAll(param);

        // 构架pageInfo
        PageInfo<AccountInfo> pageInfo = new PageInfo<>(accountList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
