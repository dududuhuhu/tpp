package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.RolePath;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RolePathService;
import com.tpp.threat_perception_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessController {
    @Autowired
    private RolePathService rolePathService;

    @PostMapping("/access/list")
    public ResponseResult accessList(MyParam param){
        return rolePathService.accessList(param);
    }

    @PostMapping("/access/save")
    public ResponseResult accessSave(@RequestBody RolePath rolePath){
        return rolePathService.saveAccess(rolePath);
    }

    @PostMapping("/access/edit")
    public ResponseResult accessEdit(@RequestBody RolePath rolePath){
        return rolePathService.editAccess(rolePath);
    }

    @PostMapping("/access/delete")
    public ResponseResult accessEdit(@RequestParam("ids[]") Integer[] ids){
        return rolePathService.deleteAccess(ids);
    }
}
