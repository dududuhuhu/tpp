package com.tpp.threat_perception_platform.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.BaselineDetectMapper;
import com.tpp.threat_perception_platform.dao.BaselineHardeningMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.param.BaselineHardenParam;
import com.tpp.threat_perception_platform.pojo.BaselineDetect;
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
    @Autowired
    private BaselineDetectMapper baselineDetectMapper;

    /**
     * 保存
     */
    @Override
    public ResponseResult saveBaselineHardening(BaselineHardening baselineHardening) {
        // 查询是否已存在记录
        BaselineHardening db = baselineHardeningMapper.selectByTaskIDAndName(
                baselineHardening.getTaskId(), baselineHardening.getName()
        );

        // 设置更新时间
        baselineHardening.setUpdatedTime(new Timestamp(System.currentTimeMillis()));

        if (db != null) {
            // 更新已有记录
            baselineHardening.setId(db.getId()); // 主键ID用于更新
            baselineHardeningMapper.updateByPrimaryKey(baselineHardening);
            return new ResponseResult<>(0, "记录已存在，已更新时间戳");
        }

        // 插入新记录
        baselineHardeningMapper.insert(baselineHardening);

        // 获取执行结果
        String result = baselineHardening.getResult();

        if ("成功".equals(result)) {
            // 若成功，加固结果写入 BaselineDetect 表
            BaselineDetect db_detect = baselineDetectMapper.selectByTaskIdAndName(
                    baselineHardening.getTaskId(), baselineHardening.getName()
            );
            if (db_detect != null) {
                db_detect.setResult("已加固");
                baselineDetectMapper.updateByPrimaryKey(db_detect);
            }
            System.out.println("加固成功，结果已记录！");
            return new ResponseResult<>(0, "加固成功，结果已记录！");
        } else {
            // 返回失败提示信息
            String errorMsg = String.format(
                    "任务 ID: %s 的基线项 [%s] 加固失败",
                    baselineHardening.getTaskId(),
                    baselineHardening.getName()
            );
            return new ResponseResult<>(1003, errorMsg);
        }
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
    public ResponseResult baselineHardeningDiscovery(BaselineHardenParam param) {
        String type = "baselineHardening";

        if (param == null || param.getMac() == null || param.getMac().isEmpty()) {
            return new ResponseResult<>(1004, "MAC 地址不能为空！");
        }

        // 根据 MAC 查询主机信息
        Host host = hostMapper.selectByMacAddress(param.getMac());
        if (host == null) {
            return new ResponseResult<>(1003, "未找到对应主机！");
        }

        // 判断是否在线，更新时间小于4秒
        Date now = new Date();
        if (host.getUpdateTime() == null || (now.getTime() - host.getUpdateTime().getTime()) > 4000) {
            return new ResponseResult<>(1003, "主机不在线！");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("taskId", param.getTaskId());

        List<String> name;
        if (param.getName() != null && !param.getName().isEmpty()) {
            name = param.getName();
        }else{
            return new ResponseResult<>(1003,"加固的基线项名为空！");
        }
        map.put("name", name);

        String json = JSON.toJSONString(map);
        String routingKey = host.getMacAddress().replace(":", "");
        rabbitService.sendMessage("agent_exchange", routingKey, json);

        return new ResponseResult<>(0, "开始同步，请稍后查看！");
    }

}
