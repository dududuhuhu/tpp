package com.tpp.threat_perception_platform.controller;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关的接口
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     * @param param
     * @return
     */
    @PostMapping("/user/list")
    public ResponseResult userList(MyParam param){
        return userService.userList(param);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/user/save")
    public ResponseResult userSave(@RequestBody User user){
        return userService.save(user);
    }
    /**
     * 编辑用户
     * @param user
     * @return
     */
    @PostMapping("/user/edit")
    public ResponseResult userEdit(@RequestBody User user){
        return userService.edit(user);
    }

    /**
     * 删除用户
     * @param
     * @return
     */
    @PostMapping("/user/delete")
    public ResponseResult userEdit(@RequestParam("ids[]") Integer[] ids){
        return userService.delete(ids);
    }

}
