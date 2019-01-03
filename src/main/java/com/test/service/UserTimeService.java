package com.test.service;

import com.test.po.Usertime;
import com.test.utils.StatsBean;
import java.util.Date;
import java.util.List;

public interface UserTimeService {



    /**
     * @param pageSize 每页显示的行数
     * @param start    查询开始日期
     * @param end      查询借宿日期
     * @param name     员工姓名
     * @param now      当前页码数
     * @return
     */
    List<Usertime> selectByDateAndName(int now, int pageSize, Date start, Date end, String name);

    StatsBean counts(Date start, Date end, String name);

    void insertBatch(List<Usertime> dataList);

}
