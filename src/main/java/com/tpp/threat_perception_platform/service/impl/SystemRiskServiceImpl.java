package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.SystemRiskMapper;
import com.tpp.threat_perception_platform.dao.SystemRiskRulesMapper;
import com.tpp.threat_perception_platform.param.SystemRiskParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRisk;
import com.tpp.threat_perception_platform.pojo.SystemRisk;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.SystemRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemRiskServiceImpl implements SystemRiskService {

    @Autowired
    private SystemRiskMapper systemRiskMapper;

    @Autowired
    private SystemRiskRulesMapper systemRiskRuleMapper;

    @Override
    public ResponseResult systemRiskList(SystemRiskParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<SystemRisk> list = systemRiskMapper.selectByParam(param);
        // 构架pageInfo
        PageInfo<SystemRisk> pageInfo = new PageInfo<>(list);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult saveSystemRisk(SystemRisk systemRisk) {
        try {
            // 设置创建时间，如果为空
            if (systemRisk.getCreatedAt() == null) {
                systemRisk.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            }

            // 更新时间直接设当前时间
            systemRisk.setUpdatedAt(new Date());

            int insertResult = systemRiskMapper.insertSelective(systemRisk);
            System.out.println("插入结果: " + insertResult);
            return new ResponseResult<>(0, "插入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(-1, "保存失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseResult editSystemRisk(SystemRisk systemRisk) {
        try {
            // 编辑时更新更新时间
            systemRisk.setUpdatedAt(new Date());
            int updateResult = systemRiskMapper.updateByPrimaryKeySelective(systemRisk);
            System.out.println("更新结果: " + updateResult);
            return new ResponseResult<>(0, "编辑成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult<>(-1, "编辑失败: " + e.getMessage());
        }
    }

    @Override
    public ResponseResult getSystemRiskDetail(Integer id) {
        SystemRisk risk = systemRiskMapper.selectByPrimaryKey(Long.valueOf(id));
        if (risk == null) {
            return new ResponseResult<>(-1, "风险记录不存在");
        }
        return new ResponseResult<>(0, risk);
    }

    @Override
    public ResponseResult getRiskCount(SystemRiskParam param) {
        // 统计数据
        int totalScans = systemRiskMapper.countTotalScans(param);
        int foundRisks = systemRiskMapper.countFoundRisks(param);
        int scanErrors = systemRiskMapper.countScanErrors(param);

        double successRate = totalScans == 0 ? 0 : (totalScans - scanErrors) * 100.0 / totalScans;

        Map<Integer, Integer> riskLevelDist = systemRiskMapper.groupCountByRiskLevel(param);
        Map<String, Integer> riskTypeDist = systemRiskMapper.groupCountByRiskType(param);

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
