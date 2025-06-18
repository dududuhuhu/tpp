package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.LoginLogMapper;
import com.tpp.threat_perception_platform.param.LogParam;
import com.tpp.threat_perception_platform.pojo.LoginLog;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    /**
     * 保存
     * @param loginLog
     * @return
     */
    @Override
    public ResponseResult saveLoginLog(LoginLog loginLog) {
        // 先查询是否已存在
        LoginLog db = loginLogMapper.selectByMacAndUsernameAndLoginTime(loginLog.getMac(), loginLog.getUsername(),loginLog.getLoginTime());
        if (db != null) {
            return new ResponseResult<>(1003, "该记录已存在！");
        }

        // 添加
        loginLogMapper.insert(loginLog);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 查询
     * @return
     */
    @Override
    public ResponseResult loginLogList(LogParam param){
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<LoginLog> loginLogList = loginLogMapper.findAll();
        // 构架pageInfo
        PageInfo<LoginLog> pageInfo = new PageInfo<>(loginLogList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

}
