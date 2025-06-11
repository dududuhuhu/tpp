package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;

public interface RoleService {
    ResponseResult addRole(Role role);

    ResponseResult updateRole(Role role);

    ResponseResult deleteRole(Long[] roleIds);

    ResponseResult listRole(MyParam param);
}
