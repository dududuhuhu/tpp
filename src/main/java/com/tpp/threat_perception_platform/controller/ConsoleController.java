package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.dao.LoginLogMapper;
import com.tpp.threat_perception_platform.pojo.InTime;
import com.tpp.threat_perception_platform.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ConsoleController {

    @Autowired
    private UserService userService;
    @Autowired
    private HostService hostService;
    @Autowired
    private VulnerabilityService vulnerabilityService;
    @Autowired
    private ApplicationRiskService applicationRiskService;
    @Autowired
    private SystemRiskService systemRiskService;
    @Autowired
    private LoginLogMapper loginLogMapper;
    @Autowired
    private InTimeService inTimeService;


    @GetMapping("/console/stats/summary")
    public Map<String, Integer> getSummaryStats() {
        Map<String, Integer> result = new HashMap<>();
        result.put("totalUsers", userService.countUsers());
        result.put("totalHosts", hostService.countHosts());
        result.put("totalVulns", vulnerabilityService.countVulnerabilities());
        result.put("totalAppRisks", applicationRiskService.countApplicationRisks());
        result.put("totalSysRisks", systemRiskService.countSystemRisks());
        System.out.println("summary:"+result);
        return result;
    }


    @GetMapping("/console/stats/host-systems")
    public List<Map<String, Object>> getHostSystemStats() {
        // 从数据库获取统计数据，返回 List<Map<String, Object>>
        // Map结构示例: {"name":"Windows","value":40}
        return hostService.getHostSystemStats();
    }

    @GetMapping("/console/stats/login-abnormal")
    public List<Map<String, Object>> getLoginAbnormalStats() {
        return loginLogMapper.getLoginAbnormalStats();
    }

    @GetMapping("/console/stats/alerts")
    public List<Map<String, Object>> getAlerts() {
        List<InTime> entities = inTimeService.getLatestAlerts(20);

        List<Map<String, Object>> result = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (InTime e : entities) {
            Map<String, Object> map = new HashMap<>();
            map.put("level", mapRiskLevel(e.getRiskLevel())); // 直接调用私有映射方法
            map.put("time", sdf.format(e.getEventTime()));
            map.put("message", e.getEvent());
            map.put("mac", e.getMac());
            result.add(map);
        }
        return result;
    }

    /**
     * 风险等级数字转文字映射
     */
    private String mapRiskLevel(Integer level) {
        if (level == null) return "未知";
        switch(level) {
            case 1: return "低";
            case 2: return "中";
            case 3: return "高";
            case 4: return "严重";
            default: return "未知";
        }
    }

}
