package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.pojo.RolePath;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface RolePathService {

    ResponseResult accessList(MyParam param);


    void updateAccess(RolePath rolePath);


    ResponseResult saveAccess(RolePath rolePath);


    ResponseResult editAccess(RolePath rolePath);


    ResponseResult deleteAccess(Integer[] ids);
}
