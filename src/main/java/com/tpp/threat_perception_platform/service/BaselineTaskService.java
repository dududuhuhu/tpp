package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.BaselineTask;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface BaselineTaskService {
    ResponseResult taskList(MyParam param);

    ResponseResult taskSave(BaselineTask baselineTask);

    ResponseResult taskEdit(BaselineTask baselineTask);

    ResponseResult taskDelete(Integer[] ids);
}
