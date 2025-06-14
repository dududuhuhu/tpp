package com.tpp.threat_perception_platform.service.impl;

import com.tpp.threat_perception_platform.dao.ApplicationRiskMapper;
import com.tpp.threat_perception_platform.dao.ApplicationRiskRulesMapper;
import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRisk;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ApplicationRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApplicationRiskServiceImpl implements ApplicationRiskService {

    @Autowired
    private ApplicationRiskMapper applicationRiskMapper;

    @Autowired
    private ApplicationRiskRulesMapper applicationRiskRuleMapper;

    @Override
    public ResponseResult appRiskList(ApplicationRiskParam param) {
        List<ApplicationRisk> list = applicationRiskMapper.selectByParam(param);
        // 假设有分页总数，或者直接用list.size()，视你Mapper实现而定
        long count = list != null ? list.size() : 0L;
        return new ResponseResult<>(count, list);
    }

    @Override
    public ResponseResult saveAppRisk(ApplicationRisk appRisk) {
        try {
            appRisk.setDetectionTime(appRisk.getDetectionTime() != null ? appRisk.getDetectionTime() : new Date());
            appRisk.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            int insertResult = applicationRiskMapper.insertSelective(appRisk);
            System.out.println("插入结果: " + insertResult);
            return new ResponseResult<>(0, "插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(-1, "保存失败: " + e.getMessage());
        }
    }



    @Override
    public ResponseResult editAppRisk(ApplicationRisk appRisk) {
        applicationRiskMapper.updateByPrimaryKeySelective(appRisk);
        return new ResponseResult<>(0, "编辑成功");
    }

    @Override
    public ResponseResult getAppRiskDetail(Integer id) {
        ApplicationRisk risk = applicationRiskMapper.selectByPrimaryKey(Long.valueOf(id));
        if (risk == null) {
            return new ResponseResult<>(-1, "风险记录不存在");
        }
        return new ResponseResult<>(0, risk);
    }


    @Override
    public ResponseResult getRiskCount(ApplicationRiskParam param) {
        // 统计总扫描次数，发现风险数，错误数，成功率等
        // 统计风险等级分布，风险类型分布

        // 假设数据库有字段：
        // scan_time, risk_level(int), risk_type(string), scan_status (0=成功,1=错误)

        int totalScans = applicationRiskMapper.countTotalScans(param);
        int foundRisks = applicationRiskMapper.countFoundRisks(param);
        int scanErrors = applicationRiskMapper.countScanErrors(param);

        double successRate = totalScans == 0 ? 0 : (totalScans - scanErrors) * 100.0 / totalScans;

        // 风险等级分布，返回Map<Integer, Integer>，key等级，value数量
        Map<Integer, Integer> riskLevelDist = applicationRiskMapper.groupCountByRiskLevel(param);

        // 风险类型分布，Map<String, Integer>
        Map<String, Integer> riskTypeDist = applicationRiskMapper.groupCountByRiskType(param);

        Map<String, Object> report = new HashMap<>();
        report.put("totalScans", totalScans);
        report.put("foundRisks", foundRisks);
        report.put("scanErrors", scanErrors);
        report.put("successRate", String.format("%.1f%%", successRate));
        report.put("riskLevelDist", riskLevelDist);
        report.put("riskTypeDist", riskTypeDist);

        return new ResponseResult<>(0, "统计成功", report);
    }


}
