package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.SystemRiskParam;
import com.tpp.threat_perception_platform.pojo.SystemRisk;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

public interface SystemRiskMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SystemRisk record);

    int insertSelective(SystemRisk record);

    SystemRisk selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SystemRisk record);

    int updateByPrimaryKey(SystemRisk record);

    // 新增：按参数查询列表
    List<SystemRisk> selectByParam(SystemRiskParam param);

    // 新增：统计总扫描次数
    int countTotalScans(SystemRiskParam param);

    // 新增：统计已发现风险数
    int countFoundRisks(SystemRiskParam param);

    // 新增：统计扫描错误数
    int countScanErrors(SystemRiskParam param);

    // 新增：按风险等级分组统计
    @MapKey("key")
    Map<Integer, Integer> groupCountByRiskLevel(SystemRiskParam param);

    // 新增：按风险类型分组统计
    @MapKey("key")
    Map<String, Integer> groupCountByRiskType(SystemRiskParam param);
}
