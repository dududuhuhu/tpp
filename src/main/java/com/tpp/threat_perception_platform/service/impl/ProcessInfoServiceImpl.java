package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.ProcessInfoMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.ProcessInfo;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.ProcessInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProcessInfoServiceImpl implements ProcessInfoService {

    @Autowired
    private ProcessInfoMapper processInfoMapper;

    @Override
    public ResponseResult save(ProcessInfo processInfo) {
        if (processInfo == null || processInfo.getMac() == null || processInfo.getMac().isEmpty()) {
            return new ResponseResult(1001, "MAC地址不能为空");
        }
        if (processInfo.getName() == null || processInfo.getName().isEmpty()) {
            return new ResponseResult(1002, "进程名称不能为空");
        }

        // 检查是否存在重复记录
        ProcessInfo exist = processInfoMapper.selectByMacAndPid(processInfo.getMac(), processInfo.getPid());
        if (exist != null) {
            return new ResponseResult(1003, "该进程已存在");
        }

        processInfo.setCollectTime(new Timestamp(System.currentTimeMillis()));
        int result = processInfoMapper.insert(processInfo);
        if (result > 0) {
            return new ResponseResult(0, "保存成功");
        } else {
            return new ResponseResult(1004, "保存失败");
        }
    }

    @Override
    public ResponseResult getByMac(String mac) {
        List<ProcessInfo> processInfos = processInfoMapper.selectByMac(mac);
        if (processInfos == null || processInfos.isEmpty()) {
            return new ResponseResult(404, "未找到进程信息", null);
        }
        return new ResponseResult(200, "成功", processInfos);
    }

    @Override
    public ResponseResult processInfoList(MyParam param) {
        String mac= param.getMacAddress();
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<ProcessInfo> processInfoList = processInfoMapper.selectByMac(mac);
        // 构架pageInfo
        PageInfo<ProcessInfo> pageInfo = new PageInfo<>(processInfoList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}