package com.test.dao;

import com.test.po.Usermanger;
import org.springframework.stereotype.Repository;

@Repository
public interface UsermangerMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(Usermanger record);

    int insertSelective(Usermanger record);

    Usermanger selectByPrimaryKey(String username);

    int updateByPrimaryKeySelective(Usermanger record);

    int updateByPrimaryKey(Usermanger record);
}