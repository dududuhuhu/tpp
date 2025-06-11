package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RoleService;
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
        return roleService.listRole(param);
    }

    @PostMapping("/role/add")
    public ResponseResult addRole(@RequestBody Role role) {
        System.out.println("here");
        System.out.println(role);
        return roleService.addRole(role);
    }

    @PostMapping("role/update")
    public ResponseResult updateRole(@RequestBody Role role) {
        return roleService.updateRole(role);
    }

    @PostMapping("/role/delete")
    public ResponseResult deleteRole(@RequestParam("ids[]") Long[] ids) {
        return roleService.deleteRole(ids);
    }
}
