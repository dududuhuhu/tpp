package com.tpp.threat_perception_platform.pojo;

import java.util.Date;
import java.util.Objects;

/**
 * 
 * @TableName process_info
 */
public class ProcessInfo {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private String mac;

    /**
     * 
     */
    private Integer pid;

    /**
     * 
     */
    private Integer ppid;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String cmd;

    /**
     * 
     */
    private Integer priority;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private Date collectTime;

    public Integer getIsHarmful() {
        return isHarmful;
    }

    public void setIsHarmful(Integer isHarmful) {
        this.isHarmful = isHarmful;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProcessInfo that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(mac, that.mac) && Objects.equals(pid, that.pid) && Objects.equals(ppid, that.ppid) && Objects.equals(name, that.name) && Objects.equals(cmd, that.cmd) && Objects.equals(priority, that.priority) && Objects.equals(description, that.description) && Objects.equals(collectTime, that.collectTime) && Objects.equals(isHarmful, that.isHarmful) && Objects.equals(harmfulKey, that.harmfulKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mac, pid, ppid, name, cmd, priority, description, collectTime, isHarmful, harmfulKey);
    }

    /**
     *
     */
    private Integer isHarmful;

    public String getHarmfulKey() {
        return harmfulKey;
    }

    public void setHarmfulKey(String harmfulKey) {
        this.harmfulKey = harmfulKey;
    }

    @Override
    public String toString() {
        return "ProcessInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", pid=" + pid +
                ", ppid=" + ppid +
                ", name='" + name + '\'' +
                ", cmd='" + cmd + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                ", collectTime=" + collectTime +
                ", isHarmful=" + isHarmful +
                ", harmfulKey='" + harmfulKey + '\'' +
                '}';
    }

    /**
     *
     */
    private String harmfulKey;


    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getMac() {
        return mac;
    }

    /**
     * 
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    /**
     * 
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 
     */
    public Integer getPpid() {
        return ppid;
    }

    /**
     * 
     */
    public void setPpid(Integer ppid) {
        this.ppid = ppid;
    }

    /**
     * 
     */
    public String getName() {
        return name;
    }

    /**
     * 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * 
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * 
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * 
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

}