package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.BaselineTaskMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaselineTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class BaselineTaskServiceImpl implements BaselineTaskService {

    @Autowired
    private BaselineTaskMapper baselineTaskMapper;

    @Override
    public ResponseResult taskList(MyParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<BaselineTask> taskList = baselineTaskMapper.findAll(param);
        // 使用日志记录
        System.out.println("Task List: " + taskList);
        // 构架pageInfo
        PageInfo<BaselineTask> pageInfo = new PageInfo<>(taskList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public ResponseResult taskSave(BaselineTask baselineTask) {
        // 先查询 是否有用户
        BaselineTask db_task = baselineTaskMapper.selectByName(baselineTask.getTaskName());
        if ( db_task!= null){
            return new ResponseResult<>(1003, "任务名已存在！");
        }
        // 任务状态默认为0未完成
        baselineTask.setTaskStatus(0);
        // 添加
        baselineTaskMapper.insertSelective(baselineTask);
        return new ResponseResult<>(0, "添加成功！");
    }

    @Override
    public ResponseResult taskEdit(BaselineTask baselineTask) {
        // 先确定这个任务是否已完成
        BaselineTask db_task = baselineTaskMapper.selectByPrimaryKey(Long.valueOf(baselineTask.getId()));
        if (db_task.getTaskStatus()==1){
            return new ResponseResult<>(1003, "任务已完成，不能修改！");
        }
        baselineTaskMapper.updateByPrimaryKeySelective(baselineTask);
        return new ResponseResult<>(0, "更新成功！");
    }

    @Override
    public ResponseResult taskDelete(Integer[] ids) {
        baselineTaskMapper.delete(ids);
        return new ResponseResult<>(0, "删除成功！");
    }
}
