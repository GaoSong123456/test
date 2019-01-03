package com.test.dao;

import com.test.po.Usertime;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface UsertimeMapper {

    List<Usertime> selectByDateAndName(@Param("start") Date start,@Param("end") Date end,@Param("name") String name);

    /**
     * 批量插入
     * @param dataList
     */
    void insertBatch(@Param("dataList") List<Usertime> dataList);
}