package com.tpp.threat_perception_platform.dao;

import com.tpp.threat_perception_platform.param.MyParam;
import com.tpp.threat_perception_platform.pojo.AccountInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author dawn
* @description 针对表【account_info】的数据库操作Mapper
* @createDate 2025-06-11 09:47:18
* @Entity com.tpp.threat_perception_platform.pojo.AccountInfo
*/
public interface AccountInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(AccountInfo record);

    int insertSelective(AccountInfo record);

    AccountInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AccountInfo record);

    int updateByPrimaryKey(AccountInfo record);
    List<AccountInfo> findAll(@Param("param") MyParam param);

}

