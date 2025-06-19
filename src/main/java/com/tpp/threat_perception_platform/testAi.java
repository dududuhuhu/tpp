package com.tpp.threat_perception_platform;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.tpp.threat_perception_platform.utils.AIUtils;


public class testAi {
    public static void main(String[] args) {
        try {
//            修改提示词prompt即可
            String prompt="根据公开资料，目前联合国有多少个成员国？请简要回答。";
            String result= AIUtils.callWithMessage(prompt).getOutput().getChoices().get(0).getMessage().getContent();
            System.out.println("回复："+result);
        } catch (NoApiKeyException | InputRequiredException | ApiException e) {
            System.err.println("调用失败：" + e.getMessage());
        }
    }
}
