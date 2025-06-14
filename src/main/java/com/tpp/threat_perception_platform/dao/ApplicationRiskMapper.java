package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.ApplicationRiskParam;
import com.tpp.threat_perception_platform.pojo.ApplicationRisk;
import org.apache.ibatis.annotations.MapKey;

import java.util.List;
import java.util.Map;

/**
* @author 25572
* @description 针对表【application_risk(应用风险检测结果表)】的数据库操作Mapper
* @createDate 2025-06-13 10:20:35
* @Entity com.tpp.threat_perception_platform.pojo.ApplicationRisk
*/
public interface ApplicationRiskMapper {

    int deleteByPrimaryKey(Long id);

    int insert(ApplicationRisk record);

    int insertSelective(ApplicationRisk record);

    ApplicationRisk selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ApplicationRisk record);

    int updateByPrimaryKey(ApplicationRisk record);

    /**
     * 根据查询参数分页查询应用风险列表
     */
    List<ApplicationRisk> selectByParam(ApplicationRiskParam param);

    /**
     * 根据查询参数统计符合条件的风险总数
     */
    int countByParam(ApplicationRiskParam param);

    int countTotalScans(ApplicationRiskParam param);
    int countFoundRisks(ApplicationRiskParam param);
    int countScanErrors(ApplicationRiskParam param);

    @MapKey("key")
    Map<Integer, Integer> groupCountByRiskLevel(ApplicationRiskParam param);

    @MapKey("key")
    Map<String, Integer> groupCountByRiskType(ApplicationRiskParam param);




}
