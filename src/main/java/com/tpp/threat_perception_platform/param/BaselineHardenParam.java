package com.tpp.threat_perception_platform.param;

import java.util.List;

public class BaselineHardenParam {
    private List<String> name;  // 基线名称列表
    private String mac;          // 设备MAC地址

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    private String taskId;      // 任务ID

    // Getter 和 Setter
    public List<String> getName() {
        return name;
    }
    public void setName(List<String> name) {
        this.name = name;
    }
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
}
