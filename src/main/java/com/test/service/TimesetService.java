package com.test.service;

import com.test.po.Timeset;

public interface TimesetService {
    /**
     * 查询设置的上下班时间
     * @return
     */
    Timeset selectByprimarykey();

    /**
     * 修改上下班时间
     * @return
     */
    int updateTimeset(Timeset timeset);
}
