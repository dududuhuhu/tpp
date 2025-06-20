package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.AcctChgLog;
import com.tpp.threat_perception_platform.response.ResponseResult;

import java.util.List;

public interface AcctChgLogService {

    /**
     * 保存账号变更日志数据（批量）
     * @param mac 主机MAC地址
     * @param actions 账号变更日志列表
     * @return 保存的记录数
     */
    Integer saveAcctChgLog(String mac, List<LogParam.Action> actions);

    /**
     * 根据主机ID或MAC查询账号变更日志
     * @param mac 主机MAC地址（或可扩展hostId）
     * @return 查询结果封装
     */
    ResponseResult<List<AcctChgLog>> getAcctChgLogByMac(String mac);

    /**
     * 支持根据条件筛选账号变更日志
     * @param param 查询参数（可以包含时间范围、操作者、被操作者等）
     * @return 查询结果封装
     */
    ResponseResult<List<AcctChgLog>> queryAcctChgLog(LogParam param);

    /**
     * 分页查询账号变更日志列表，支持按条件过滤
     * @param param 查询参数，包括分页和筛选条件
     * @return 分页结果，包含总数和当前页数据列表
     */
    ResponseResult<List<AcctChgLog>> listAcctChgLog(LogParam param);

    ResponseResult accountChangeLogDiscovery();
}
