package com.tpp.threat_perception_platform.service;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.Role;
import com.tpp.threat_perception_platform.response.ResponseResult;


public interface RoleService {

    /**
     * 角色列表
     * @param param
     * @return
     */
    ResponseResult roleList(MyParam param);

    /**
     * 更新角色
     * @param role
     */
    void updateRole(Role role);

    /**
     * 保存角色、增加角色
     * @param role
     * @return
     */
    ResponseResult saveRole(Role role);

    /**
     * 编辑角色
     * @param role
     * @return
     */
    ResponseResult editRole(Role role);

    /**
     * 删除角色
     * @param ids
     * @return
     */
    ResponseResult deleteRole(Integer[] ids);


}
