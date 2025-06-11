package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.AccountInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {


    @Autowired
    private AccountInfoMapper accountInfoMapper;

    @Override
    public int saveAccountInfo(AccountInfo accountInfo) {
        return accountInfoMapper.insertSelective(accountInfo);
    }

    @Override
    public ResponseResult accountList(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<AccountInfo> accountList = accountInfoMapper.findAll(param);

        // 构架pageInfo
        PageInfo<AccountInfo> pageInfo = new PageInfo<>(accountList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
