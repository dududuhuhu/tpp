package com.tpp.threat_perception_platform.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 
 * @TableName baseline_task
 */
public class BaselineTask {
    /**
     * 主键自增ID
     */
    private Integer id;

    /**
     * 任务
名
     */
    private String taskName;

    /**
     * 任务执行时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") // 处理表单提交
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date taskTime;

    /**
     * 
     */
    private Integer taskStatus;

    /**
     * 需要执行任务的主机mac地址
     */
    private String taskHosts;

    /**
     * 主键自增ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键自增ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 任务
名
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * 任务
名
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 任务执行时间
     */
    public Date getTaskTime() {
        return taskTime;
    }

    /**
     * 任务执行时间
     */
    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    /**
     * 
     */
    public Integer getTaskStatus() {
        return taskStatus;
    }

    /**
     * 
     */
    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    /**
     * 需要执行任务的主机mac地址
     */
    public String getTaskHosts() {
        return taskHosts;
    }

    /**
     * 需要执行任务的主机mac地址
     */
    public void setTaskHosts(String taskHosts) {
        this.taskHosts = taskHosts;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        BaselineTask other = (BaselineTask) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTaskName() == null ? other.getTaskName() == null : this.getTaskName().equals(other.getTaskName()))
            && (this.getTaskTime() == null ? other.getTaskTime() == null : this.getTaskTime().equals(other.getTaskTime()))
            && (this.getTaskStatus() == null ? other.getTaskStatus() == null : this.getTaskStatus().equals(other.getTaskStatus()))
            && (this.getTaskHosts() == null ? other.getTaskHosts() == null : this.getTaskHosts().equals(other.getTaskHosts()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTaskName() == null) ? 0 : getTaskName().hashCode());
        result = prime * result + ((getTaskTime() == null) ? 0 : getTaskTime().hashCode());
        result = prime * result + ((getTaskStatus() == null) ? 0 : getTaskStatus().hashCode());
        result = prime * result + ((getTaskHosts() == null) ? 0 : getTaskHosts().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", taskName=").append(taskName);
        sb.append(", taskTime=").append(taskTime);
        sb.append(", taskStatus=").append(taskStatus);
        sb.append(", taskHosts=").append(taskHosts);
        sb.append("]");
        return sb.toString();
    }
}