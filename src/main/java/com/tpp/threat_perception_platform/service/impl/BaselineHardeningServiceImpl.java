package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.BaselineHardeningMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.pojo.BaselineHardening;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaselineHardeningService;
import com.tpp.threat_perception_platform.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaselineHardeningServiceImpl implements BaselineHardeningService {

    @Autowired
    private BaselineHardeningMapper baselineHardeningMapper;

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private RabbitService rabbitService;

    /**
     * 保存
     */
    @Override
    public ResponseResult saveBaselineHardening(BaselineHardening baselineHardening) {
        // 先查询是否已存在
        BaselineHardening db = baselineHardeningMapper.selectByMacAndName(baselineHardening.getMac(), baselineHardening.getName());
        // 添加
        baselineHardening.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        if (db != null) {
            // 若已存在，更新字段（只更新 updated_time 或者所有字段）
            // 方式 1：只更新 updated_time
            // db.setUpdatedTime(now);
            // baselineDetectMapper.updateUpdatedTimeById(db);

            // 方式 2：更新所有字段（推荐）
            baselineHardening.setId(db.getId()); // 设置主键，用于 where 条件
            baselineHardeningMapper.updateByPrimaryKey(baselineHardening);

            return new ResponseResult<>(0, "记录已存在，已更新时间戳");
        }
        baselineHardeningMapper.insert(baselineHardening);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 查询列表（分页）
     */
    @Override
    public ResponseResult baselineHardeningList(BaselineDetectParam param) {
        String mac= param.getMac();
        System.out.println("mac:"+mac);
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<BaselineHardening> baselineHardeningList = baselineHardeningMapper.findAll();
        // 构架pageInfo
        PageInfo<BaselineHardening> pageInfo = new PageInfo<>(baselineHardeningList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult baselineHardeningDiscovery() {
        String type="baselineHardening";
        List<Host> db_hostList = hostMapper.findAll();
        if(db_hostList.isEmpty()){
            return new ResponseResult<>(1003,"无主机在线！");
        }
        for(Host host : db_hostList){
            // 去比较更新时间和当前时间判断主机是否在线
            if (host != null && host.getUpdateTime() != null && new Date().getTime() - host.getUpdateTime().getTime() < 4000)
            {
                Map<String, Object> map = new HashMap<>();
                map.put("type", "baselineHardening");
                // 名字的获取没写
//                List<String> names = accountInfoMapper.selectAllNamesByMac(host.getMacAddress());
//                map.put("username",names);
                String json = JSON.toJSONString(map);  // 结果是 {"type":"auditLog"}
                // 组装队列的名字
                String routingKey=host.getMacAddress().replace(":","");
                rabbitService.sendMessage("agent_exchange",routingKey,json);
            }
        }
        return new ResponseResult(0, "开始同步，请稍后查看！");
    }
}
