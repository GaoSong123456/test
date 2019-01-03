/**
 * Copyright (C), 2018-2018, 中兴微品
 * FileName: UserTimeController
 * Author:   Administrator
 * Date:     2018/7/24 14:38
 * Description: UserTimeController
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.controller;


import com.github.pagehelper.PageInfo;
import com.test.po.Usertime;
import com.test.service.UserTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 〈UserTimeController〉
 *
 * @author Administrator
 * @create 2018/7/24
 * @since 1.0.0
 */

@Controller
public class UserTimeController {
    @Autowired
    private UserTimeService userTimeService;
    //开始时间，回显
    String begindates;
    //结束时间，回显
    String enddates;

    @RequestMapping("selectAll.do")
    public String selectData(@RequestParam(value = "begindate", required = false, defaultValue = "2018-07-01") Date begindate, String username,
                             @RequestParam(value = "enddate", required = false, defaultValue = "2018-12-31") Date enddate,
                             @RequestParam(value = "now", required = false, defaultValue = "1") Integer now, ModelMap map) {

        //date类型做回显
        SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd");
        //查询分页，考勤状态设置
        List<Usertime> usertimeList = userTimeService.selectByDateAndName(now, 10, begindate, enddate, username);
        System.out.println("总记录信息:" + usertimeList.size());
        PageInfo<Usertime> Page_Info = new PageInfo<Usertime>(usertimeList);
        map.put("Page_Info", Page_Info);
        begindates = sdp.format(begindate);
        enddates = sdp.format(enddate);
        map.put("begin", begindates);
        map.put("username", username);
        map.put("end", enddates);

        return "index";
    }


}
