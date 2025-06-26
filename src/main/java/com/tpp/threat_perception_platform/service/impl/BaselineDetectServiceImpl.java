package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.BaselineDetectMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
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
    public ResponseResult saveBaselineDetect(BaselineDetect baselineDetect, Timestamp now) {
        // 先查询是否已存在
        BaselineDetect db = baselineDetectMapper.selectByTaskIdAndName(baselineDetect.getTaskId(),baselineDetect.getName());
        // 添加
        baselineDetect.setUpdatedTime(now);
        if (db != null) {
            // 应该用不到
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
        Integer taskId= param.getTaskId();
        System.out.println("taskId:"+taskId);
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<BaselineDetect> baselineDetectList;
        if(taskId!=null){
            baselineDetectList = baselineDetectMapper.findByTaskId(taskId);
        }else{
            baselineDetectList = baselineDetectMapper.findAll();
        }
        // 构架pageInfo
        PageInfo<BaselineDetect> pageInfo = new PageInfo<>(baselineDetectList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult baselineDetectDiscovery(BaselineTask task) {
        System.out.println("开始基线核查命令下达！");
        Integer taskId=task.getId();
        String type="baselineDetect";
        Host dbHost = hostMapper.selectByMacAddress(task.getTaskHosts());
        // 查询主机状态，确认在线
        // 设成二十秒是因为，重新启动程序需要差不多20s，若要测试当然是已启动就执行任务
        if (dbHost == null || dbHost.getUpdateTime() == null ||
                new Date().getTime() - dbHost.getUpdateTime().getTime() > 20000) {
            System.out.println("基线任务目标不在线！");
            return new ResponseResult<>(1003, "主机不在线！");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("taskId", taskId);
        String json = JSON.toJSONString(map);
        System.out.println("将要发送到队列的任务参数："+json);
        // 组装队列的名字
        String routingKey=task.getTaskHosts().replace(":","");
        rabbitService.sendMessage("agent_exchange",routingKey,json);
        return new ResponseResult(0, "已发送基线核查任务命令，请稍后查看！");
    }
}
