package com.test.dao;

import com.test.po.Timeset;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TimesetMapper {


    /**
     * 查询设置的上下班时间
     * @return
     */
   Timeset selectByPrimarykey();

    /**
     * 修改上下班时间
     * @return
     */
    int updateTimeset(@Param("timeset") Timeset timeset);
}
