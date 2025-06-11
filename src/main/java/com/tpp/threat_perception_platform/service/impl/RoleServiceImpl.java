package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.RoleMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResponseResult roleList(MyParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<Role> roleList = roleMapper.findAll(param);
        // 构架pageInfo
        PageInfo<Role> pageInfo = new PageInfo<>(roleList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    /**
     * 角色更新
     * @param role
     */
    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    /**
     * 角色保存
     * @param role
     * @return
     */
    @Override
    public ResponseResult saveRole(Role role) {
        // 先查询 是否有该角色
        Role db_role = roleMapper.selectByRoleName(role.getRoleName());
        if ( db_role!= null){
            return new ResponseResult<>(1003, "角色已存在！");
        }

        // 添加
        roleMapper.insertSelective(role);
        return new ResponseResult<>(0, "添加成功！");
    }

    /**
     * 更新角色
     * @param role
     * @return
     */
    @Override
    public ResponseResult editRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
        return new ResponseResult<>(0, "更新成功！");
    }

    /**
     * 删除角色
     * @param ids
     * @return
     */
    @Override
    public ResponseResult deleteRole(Integer[] ids ){
        roleMapper.deleteRole(ids);
        return new ResponseResult<>(0, "删除成功！");
    }


}
