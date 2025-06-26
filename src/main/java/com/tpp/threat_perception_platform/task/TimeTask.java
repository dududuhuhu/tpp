package com.tpp.threat_perception_platform.task;

import com.tpp.threat_perception_platform.dao.BaselineDetectMapper;
import com.tpp.threat_perception_platform.dao.BaselineTaskMapper;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
import com.tpp.threat_perception_platform.service.BaselineDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TimeTask {

    @Autowired
    private BaselineTaskMapper baselineTaskMapper;  // 基线任务数据库操作接口

    @Autowired
    private BaselineDetectService baselineDetectService; // 业务层，发送检测任务

    @Autowired
    private BaselineDetectMapper baselineDetectMapper; // 基线检测结果数据库操作接口

    /**
     * 定时任务：每8分钟执行一次，查询未完成且到期任务，执行后根据检测结果更新状态
     */
    @Scheduled(fixedDelay = 60000*8)
    public void checkAndRunTasks() {
        System.out.println("定时基线任务检测");
        // 查询当前时间之前且状态为未完成（0或null）的任务列表
        List<BaselineTask> tasks = baselineTaskMapper.findPendingTasks(LocalDateTime.now());
        if (tasks.isEmpty()) {
            System.out.println("当前无待执行的基线任务");
        }
        for (BaselineTask task : tasks) {
            try {
                System.out.println("检测到任务"+task.getId()+"需要执行");
                // 发送基线检测任务（实际执行命令下发）
                runTask(task);
            } catch (Exception e) {
                e.printStackTrace();

                // 任务执行异常，更新任务状态为失败(2)
                task.setTaskStatus(2);
                baselineTaskMapper.update(task);
            }
        }
    }
    /**
     * 调用业务层发送检测任务命令
     */
    private void runTask(BaselineTask task) {
        System.out.println("执行任务: " + task.getTaskName() + " 对主机: " + task.getTaskHosts());
        baselineDetectService.baselineDetectDiscovery(task);
    }
}
