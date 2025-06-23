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
    public int analyzeAndSaveAccountInfo(AccountInfo accountInfo) {
        // 先查询数据库是否存在该账号，假设唯一键是 mac + name
        AccountInfo existing = accountInfoMapper.selectByMacAndName(accountInfo.getMac(), accountInfo.getName());

        if (existing != null) {
            // 判断关键字段是否有变化（可根据实际情况添加更多字段）
            boolean isChanged = false;
            if (!Objects.equals(existing.getFullName(), accountInfo.getFullName()) ||
                    !Objects.equals(existing.getSid(), accountInfo.getSid()) ||
                    !Objects.equals(existing.getSidType(), accountInfo.getSidType()) ||
                    !Objects.equals(existing.getStatus(), accountInfo.getStatus()) ||
                    !Objects.equals(existing.getDisabled(), accountInfo.getDisabled()) ||
                    !Objects.equals(existing.getLockout(), accountInfo.getLockout()) ||
                    !Objects.equals(existing.getPasswordChangeable(), accountInfo.getPasswordChangeable()) ||
                    !Objects.equals(existing.getPasswordExpires(), accountInfo.getPasswordExpires()) ||
                    !Objects.equals(existing.getPasswordRequired(), accountInfo.getPasswordRequired())) {
                isChanged = true;
            }

            if (!isChanged && existing.getIsHarmful() != null && existing.getHarmfulKey() != null) {
                // 数据无变化且已有AI分析结果，跳过AI调用，返回成功码
                System.out.println("Account info unchanged, skip AI analysis.");
                return 1;  // 表示无更新
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

        Date now = new Date();
        accountInfo.setCreatedAt(now);
        accountInfo.setUpdatedAt(now);

        if (existing != null) {
            // 更新记录
            accountInfo.setId(existing.getId());
            return accountInfoMapper.updateByPrimaryKeySelective(accountInfo);
        } else {
            // 新增记录
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
