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
import java.util.Objects;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {


    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public int analyzeAndSaveAccountInfo(AccountInfo accountInfo, Date now) {
        // 查询数据库是否存在
        AccountInfo existing = accountInfoMapper.selectBySidAndMac(accountInfo.getMac(), accountInfo.getSid());

        if (existing != null) {
            boolean isChanged = !Objects.equals(existing.getStatus(), accountInfo.getStatus()) ||
                    !Objects.equals(existing.getDisabled(), accountInfo.getDisabled()) ||
                    !Objects.equals(existing.getLockout(), accountInfo.getLockout()) ||
                    !Objects.equals(existing.getPasswordExpires(), accountInfo.getPasswordExpires()) ||
                    !Objects.equals(existing.getPasswordRequired(), accountInfo.getPasswordRequired());

            if (!isChanged && existing.getIsHarmful() != null && existing.getHarmfulKey() != null) {
                System.out.println("Account info unchanged, skip AI analysis.");
                return 1;
            }
        }

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
                        "- 如果账号状态为 '锁定' 或 '禁用'，视为无害。\n" +
                        "- 如果 SID 类型为 '管理员' 或账号名为 'admin'、'administrator'、'root'，且密码过期，则视为有害，风险关键点为 '高权限账号密码过期'。\n" +
                        "- 如果账号密码过期，且账号未锁定或未禁用，则视为有害，风险关键点为 '密码过期未处理'。\n" +
                        "- 如果密码必需为否，且账号为管理员或高权限账号，则视为有害，风险关键点为 '高权限账号密码不必需'。\n" +
                        "- 其他情况视为无害。\n" +
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
            GenerationResult result = AIUtils.callWithMessage(prompt);
            String content = result.getOutput().getChoices().get(0).getMessage().getContent();

            content = content.replaceAll("(?i)```json", "")
                    .replaceAll("```", "")
                    .trim();

            JSONObject jsonObject = JSON.parseObject(content);
            accountInfo.setIsHarmful(jsonObject.getInteger("is_harmful"));
            accountInfo.setHarmfulKey(jsonObject.getString("harmful_key"));
        } catch (Exception e) {
            accountInfo.setIsHarmful(0);
            accountInfo.setHarmfulKey("AI分析失败: " + e.getMessage());
        }

        accountInfo.setCreatedAt(now);
        accountInfo.setUpdatedAt(now);

        if (existing != null) {
            accountInfo.setId(existing.getId());
            accountInfo.setCreatedAt(existing.getCreatedAt()); // 保留原创建时间
            accountInfo.setUpdatedAt(now);
            return accountInfoMapper.updateByPrimaryKeySelective(accountInfo);
        } else {
            accountInfo.setCreatedAt(now);
            accountInfo.setUpdatedAt(now);
            return accountInfoMapper.insertSelective(accountInfo);
        }

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
