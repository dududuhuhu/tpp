package com.tpp.threat_perception_platform.service;

import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.vo.LoginLogWithActionsVO;
import org.apache.ibatis.annotations.Param;


public interface AuditLogService {

    PageInfo<LoginLogWithActionsVO> getAuditLogList(@Param("param")LogParam param);

    ResponseResult auditLogDiscovery();
}
