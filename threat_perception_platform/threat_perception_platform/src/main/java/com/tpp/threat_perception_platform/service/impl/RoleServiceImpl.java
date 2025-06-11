package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.RoleMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResponseResult addRole(Role role) {
        Role r = roleMapper.selectByRoleName(role.getRoleName());
        if (r != null) return new ResponseResult<>(400, "Failure");
        int result = roleMapper.insertSelective(role);
        if (result > 0)
            return new ResponseResult<>(0, "Success");
        else return new ResponseResult<>(400, "Failure");
    }

    @Override
    public ResponseResult updateRole(Role role) {
        int result = roleMapper.updateByPrimaryKeySelective(role);
        if (result > 0)
            return new ResponseResult<>(0, "Success");
        else return new ResponseResult<>(400, "Failure");
    }

    @Override
    public ResponseResult deleteRole(Long[] roleIds) {
        ResponseResult response;
        for (var roleId : roleIds) {
            if(roleMapper.deleteByPrimaryKey(roleId) <= 0) {
                response = new ResponseResult<>(400, "Fail to delete role, id = " + roleId);
                return response;
            }
        }
        return new ResponseResult<>(0, "Success");
    }

    @Override
    public ResponseResult listRole(MyParam param) {
        PageHelper.startPage(param.getPage(), param.getLimit());
        List<Role> roleList = roleMapper.selectAll(param);
        PageInfo pageInfo = new PageInfo<>(roleList);
        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }
}
