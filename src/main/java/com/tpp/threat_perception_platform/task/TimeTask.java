package com.tpp.threat_perception_platform.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeTask {

    // 每5秒钟
    @Scheduled(cron = "*/5 * * * * *")
    public void timeTask1() {
        // 读取基线任务表中的配置基线任务
        // 如果满足条件，就去发送探测任务给Agent

    }
}
