package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.BaselineDetectParam;
import com.tpp.threat_perception_platform.param.BaselineHardenParam;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.BaselineDetectService;
import com.tpp.threat_perception_platform.service.BaselineHardeningService;
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
    @Autowired
    private BaselineHardeningService baselineHardeningService;

    @PostMapping("/baseline/task/list")
    public ResponseResult taskList(MyParam param){
        return baselineTaskService.taskList(param);
    }

    @PostMapping("/baseline/task/save")
    public ResponseResult taskSave(@RequestBody BaselineTask baselineTask){
        return baselineTaskService.taskSave(baselineTask);
    }

    @PostMapping("/baseline/task/edit")
    public ResponseResult taskEdit(@RequestBody BaselineTask baselineTask){
        return baselineTaskService.taskEdit(baselineTask);
    }

    @PostMapping("/baseline/task/delete")
    public ResponseResult taskEdit(@RequestParam("ids[]") Integer[] ids){
        return baselineTaskService.taskDelete(ids);
    }


    @PostMapping("/baseline/result/list")
    public ResponseResult resultList(@RequestBody BaselineDetectParam param){
        System.out.println(param.getTaskId());
        return baselineDetectService.baselineDetectList(param);
    }

    @PostMapping("/baseline/fix/batch")
    public ResponseResult resultHarden(@RequestBody BaselineHardenParam param){
        if (param == null
                || param.getName() == null || param.getName().isEmpty()
                || param.getMac() == null || param.getMac().isEmpty()
                || param.getTaskId() == null || param.getTaskId().isEmpty()) {
            return new ResponseResult<>(1, "参数不完整");
        }
        System.out.println("name:"+param.getName());
        System.out.println("mac:"+param.getMac());
        System.out.println("taskId:"+param.getTaskId());
        return baselineHardeningService.baselineHardeningDiscovery(param);
    }


}
