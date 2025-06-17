package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.pojo.AgentPublicKey;

/**
* @author lup
* @description 针对表【agent_public_key】的数据库操作Mapper
* @createDate 2025-06-17 10:54:56
* @Entity com.tpp.threat_perception_platform.pojo.AgentPublicKey
*/
public interface AgentPublicKeyMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AgentPublicKey record);

    int insertSelective(AgentPublicKey record);

    AgentPublicKey selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AgentPublicKey record);

    int updateByPrimaryKey(AgentPublicKey record);

    /*
    向数据库插入记录如果mac重复，则更新pem_pub字段
     */
    int insertAgentPublicKey(AgentPublicKey agentPublicKey);

    /*
    如果数据库中存在对应mac地址的记录，则返回这条记录否则返回null
     */
    AgentPublicKey selectByMac(String mac);



}
