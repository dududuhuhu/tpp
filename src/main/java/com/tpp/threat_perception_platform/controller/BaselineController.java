package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaselineDetectService;
import com.tpp.threat_perception_platform.service.BaselineTaskService;
import com.tpp.threat_perception_platform.service.impl.BaselineTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaselineController {

    @Autowired
    private BaselineTaskService baselineTaskService;
    @Autowired
    private BaselineDetectService baselineDetectService;

    @PostMapping("/baseline/task/list")
    public ResponseResult taskList(MyParam param){
        return baselineTaskService.taskList(param);
    }

    @PostMapping("/baseline/task/save")
    public ResponseResult taskSave(@RequestBody BaselineTask baselineTask){
        return baselineTaskService.taskSave(baselineTask);
    }

    @PostMapping("/baseline/task/edit")
    public ResponseResult userEdit(@RequestBody BaselineTask baselineTask){
        return baselineTaskService.taskEdit(baselineTask);
    }

    @PostMapping("/baseline/task/delete")
    public ResponseResult userEdit(@RequestParam("ids[]") Integer[] ids){
        return baselineTaskService.taskDelete(ids);
    }


    @PostMapping("/baseline/result/list")
    public ResponseResult resultList(@RequestBody BaselineDetectParam param){
        System.out.println(param.getTaskId());
        return baselineDetectService.baselineDetectList(param);
    }


}
