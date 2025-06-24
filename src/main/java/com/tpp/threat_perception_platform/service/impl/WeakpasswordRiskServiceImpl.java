package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.AccountInfoMapper;
import com.tpp.threat_perception_platform.dao.HostMapper;
import com.tpp.threat_perception_platform.dao.WeakpasswordRiskMapper;
import com.tpp.threat_perception_platform.param.WeakpasswordParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.pojo.WeakpasswordRisk;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.WeakpasswordRiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class WeakpasswordRiskServiceImpl implements WeakpasswordRiskService {

    @Autowired
    private WeakpasswordRiskMapper weakpasswordRiskMapper;
    @Autowired
    private HostMapper hostMapper;
    @Autowired
    private AccountInfoMapper accountInfoMapper;

    /**
     * 保存
     */
    @Override
    public ResponseResult saveWeakpasswordRisk(WeakpasswordRisk weakpasswordRisk, Timestamp now) {
        // 先查询是否已存在
        WeakpasswordRisk db = weakpasswordRiskMapper.selectByMacAndUsername(weakpasswordRisk.getMac(),weakpasswordRisk.getUsername());

        weakpasswordRisk.setUpdatedTime(now);
        if (db != null) {
            // 重新更新时间
            weakpasswordRisk.setId(db.getId());
            weakpasswordRiskMapper.updateByPrimaryKey(weakpasswordRisk);
            return new ResponseResult<>(1003, "该弱密码风险记录已存在，更新时间！");
        }

        // 更新账号信息的风险标识
        String username = weakpasswordRisk.getUsername();
        String mac = weakpasswordRisk.getMac();
        AccountInfo db_account = accountInfoMapper.selectByNameAndMac(username,mac);
        db_account.setIsHarmful(1);
        db_account.setHarmfulKey("弱密码风险");
        accountInfoMapper.updateByPrimaryKeySelective(db_account);

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
        // 查询weak = true
        List<WeakpasswordRisk> weakpasswordRiskList = weakpasswordRiskMapper.findAll();
        // 构架pageInfo
        PageInfo<WeakpasswordRisk> pageInfo = new PageInfo<>(weakpasswordRiskList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
