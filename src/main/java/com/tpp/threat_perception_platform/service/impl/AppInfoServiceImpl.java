package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.AppInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AppInfo;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.AppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class AppInfoServiceImpl implements AppInfoService {

    @Autowired
    private AppInfoMapper appInfoMapper;

    /**
     * 查询 App 列表（分页）
     */
    @Override
    public ResponseResult appList(MyParam param) {
        String mac= param.getMacAddress();
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<AppInfo> appList = appInfoMapper.findAll(mac);
        // 构架pageInfo
        PageInfo<AppInfo> pageInfo = new PageInfo<>(appList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 保存 AppInfo
     */
    @Override
    public ResponseResult saveApp(AppInfo appInfo) {
        // 先查询是否已存在（根据 mac + display_name 判断是否重复）
        AppInfo db_app = appInfoMapper.selectByMacAndDisplayName(appInfo.getMac(), appInfo.getDisplayName());
        if (db_app != null) {
            return new ResponseResult<>(1003, "该软件记录已存在！");
        }

        // 添加
        appInfo.setCollectTime(new Timestamp(System.currentTimeMillis()));
        appInfoMapper.insertSelective(appInfo);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 更新 AppInfo
     */
    @Override
    public ResponseResult editApp(AppInfo appInfo) {
        appInfoMapper.updateByPrimaryKeySelective(appInfo);
        return new ResponseResult<>(0, "更新成功！");
    }

    /**
     * 删除 AppInfo
     */
//    @Override
//    public ResponseResult deleteApp(Integer[] ids) {
//        appInfoMapper.deleteApp(ids);
//        return new ResponseResult<>(0, "删除成功！");
//    }
}