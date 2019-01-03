package com.test.controller;


import com.test.po.Timeset;
import com.test.service.TimesetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
public class TimesetController {


    @Autowired
    private TimesetService timesetService;

    @RequestMapping("updateTimeset.do")
    public String updateTimeset (Timeset timeset){
        timesetService.updateTimeset(timeset);
        System.out.println(timeset);

        return "redirect:selectAll.do";
    }
    //获取所有员工用于展示页面
    @RequestMapping("/goTimeset.do")
    public String goTimeset(ModelMap map) {
        Timeset timeset = timesetService.selectByprimarykey();
        System.out.println(timeset.toString());
        map.put("timeset", timeset);
        return "updatetime";
    }



}
