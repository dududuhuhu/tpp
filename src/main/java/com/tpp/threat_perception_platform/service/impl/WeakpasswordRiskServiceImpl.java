package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.WeakpasswordRiskMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.param.WeakpasswordParam;
import com.tpp.threat_perception_platform.pojo.AppInfo;
import com.tpp.threat_perception_platform.pojo.WeakpasswordRisk;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.WeakpasswordRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeakpasswordRiskServiceImpl implements WeakpasswordRiskService {

    @Autowired
    private WeakpasswordRiskMapper weakpasswordRiskMapper;

    /**
     * 保存
     */
    @Override
    public ResponseResult saveWeakpasswordRisk(WeakpasswordRisk weakpasswordRisk) {
        // 先查询是否已存在
        WeakpasswordRisk db = weakpasswordRiskMapper.selectByMacAndUsername(weakpasswordRisk.getMac(),weakpasswordRisk.getUsername());
        if (db != null) {
            return new ResponseResult<>(1003, "该弱密码风险记录已存在！");
        }

        // 添加
        weakpasswordRiskMapper.insert(weakpasswordRisk);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 查询列表（分页）
     */
    @Override
    public ResponseResult weakpasswordList(WeakpasswordParam param) {
        String mac= param.getMacAddress();
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<WeakpasswordRisk> weakpasswordRiskList = weakpasswordRiskMapper.findAll(mac);
        // 构架pageInfo
        PageInfo<WeakpasswordRisk> pageInfo = new PageInfo<>(weakpasswordRiskList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
