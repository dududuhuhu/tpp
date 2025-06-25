package com.tpp.threat_perception_platform.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tpp.threat_perception_platform.dao.RoleMapper;
import com.tpp.threat_perception_platform.dao.RolePathMapper;
import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.pojo.RolePath;
import com.tpp.threat_perception_platform.pojo.User;
import com.tpp.threat_perception_platform.response.ResponseResult;
import com.tpp.threat_perception_platform.service.RolePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class RolePathServiceImpl implements RolePathService {

    @Autowired
    private RolePathMapper rolePathMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public ResponseResult accessList(MyParam param) {
        // 设置分页参数
        PageHelper.startPage(param.getPage(), param.getLimit());
        // 查询所有
        List<RolePath> accessList = rolePathMapper.findAll(param);
        // 使用日志记录
        System.out.println("access List: " + accessList);
        // 构架pageInfo
        PageInfo<RolePath> pageInfo = new PageInfo<>(accessList);

        return new ResponseResult<>(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void updateAccess(RolePath rolePath) {
        rolePathMapper.updateByPrimaryKey(rolePath);
    }

    @Override
    public ResponseResult saveAccess(RolePath rolePath) {

        System.out.println("Access:"+rolePath);
        // 先查询 是否有这个角色
        Role db_role = roleMapper.selectByRoleName(rolePath.getRoleName());
        if ( db_role== null){
            return new ResponseResult<>(1003, "角色不存在！");
        }
        // 如果角色存在，添加权限
        rolePath.setRoleId(db_role.getId());
        rolePathMapper.insert(rolePath);
        return new ResponseResult<>(0, "添加成功！");
    }

    @Override
    public ResponseResult editAccess(RolePath rolePath) {
        Role db_role = roleMapper.selectByRoleName(rolePath.getRoleName());
        rolePath.setRoleId(db_role.getId());
        rolePathMapper.updateByPrimaryKey(rolePath);
        return new ResponseResult<>(0, "更新成功！");
    }

    @Override
    public ResponseResult deleteAccess(Integer[] ids) {
        rolePathMapper.delete(ids);
        return new ResponseResult<>(0, "删除成功！");
    }
}
