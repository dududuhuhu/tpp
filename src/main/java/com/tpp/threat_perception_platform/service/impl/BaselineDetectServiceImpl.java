package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.BaselineDetectMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;
import com.tpp.threat_perception_platform.pojo.Host;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaselineDetectService;
import com.tpp.threat_perception_platform.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaselineDetectServiceImpl implements BaselineDetectService {

    @Autowired
    private BaselineDetectMapper baselineDetectMapper;

    @Autowired
    private HostMapper hostMapper;

    @Autowired
    private RabbitService rabbitService;

    /**
     * 保存
     */
    @Override
    public ResponseResult saveBaselineDetect(BaselineDetect baselineDetect) {
        // 先查询是否已存在
        BaselineDetect db = baselineDetectMapper.selectByMacAndName(baselineDetect.getMac(), baselineDetect.getName());
        // 添加
        baselineDetect.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
        if (db != null) {
            // 若已存在，更新字段（只更新 updated_time 或者所有字段）
            // 方式 1：只更新 updated_time
            // db.setUpdatedTime(now);
            // baselineDetectMapper.updateUpdatedTimeById(db);

            // 方式 2：更新所有字段（推荐）
            baselineDetect.setId(db.getId()); // 设置主键，用于 where 条件
            baselineDetectMapper.updateByPrimaryKey(baselineDetect);

            return new ResponseResult<>(0, "记录已存在，已更新时间戳");
        }
        baselineDetectMapper.insert(baselineDetect);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 查询列表（分页）
     */
    @Override
    public ResponseResult baselineDetectList(BaselineDetectParam param) {
        String mac= param.getMac();
        System.out.println("mac:"+mac);
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<BaselineDetect> baselineDetectList = baselineDetectMapper.findAll();
        // 构架pageInfo
        PageInfo<BaselineDetect> pageInfo = new PageInfo<>(baselineDetectList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult baselineDetectDiscovery() {
        String type="baselineDetect";
        List<Host> db_hostList = hostMapper.findAll();
        if(db_hostList.isEmpty()){
            return new ResponseResult<>(1003,"无主机在线！");
        }
        for(Host host : db_hostList){
            // 去比较更新时间和当前时间判断主机是否在线
            if (host != null && host.getUpdateTime() != null && new Date().getTime() - host.getUpdateTime().getTime() < 4000)
            {
                Map<String, Object> map = new HashMap<>();
                map.put("type", "baselineDetect");
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
