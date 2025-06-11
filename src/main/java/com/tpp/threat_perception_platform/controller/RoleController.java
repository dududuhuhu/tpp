package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RoleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;


    @PostMapping("/role/list")
    public ResponseResult roleList(MyParam param) {
        return roleService.roleList(param);
    }

    @PostMapping("/role/save")
    public ResponseResult roleSave(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    @PostMapping("/role/edit")
    public ResponseResult roleEdit(@RequestBody Role role) {
        return roleService.editRole(role);
    }

    @PostMapping("/role/delete")
    public ResponseResult roleDelete(@RequestParam("ids[]") Integer[] ids){
        return roleService.deleteRole(ids);
    }

}
