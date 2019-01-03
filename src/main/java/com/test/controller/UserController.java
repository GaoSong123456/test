package com.test.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.test.po.User;
import com.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/selectAllUser.do")
    public String selectAllUser(ModelMap map,
                                @RequestParam(value = "now", required = false, defaultValue = "1") Integer now) {

        System.out.println(11111);
        //设置分页工具
        PageHelper.startPage(now, 10);
        List<User> users = userService.selectAllUser();
        System.out.println("员工数量" + users.size());
        //将查询的数据存入pageinfo
        PageInfo<User> pageInfo = new PageInfo<User>(users);
        map.put("Page_Info", pageInfo);

        return "userlist";
    }

    /**
     * 删除
     *
     * @param userid
     * @param resp
     */
    @RequestMapping("/del.do")
    public void delById(int id, HttpServletResponse resp) {
        int i = userService.deleteByPrimaryKey(id);
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter printWriter = null;
        try {
            printWriter = resp.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (i > 0) {
            printWriter.write("<script type='text/javascript' >alert('删除成功');location='/selectAllUser.do';</script>");
        } else {
            printWriter.write("<script type='text/javascript' >alert('删除失败');</script>");
        }
    }

    //获取员工列表
    @RequestMapping("/addUser.do")
    public String addUser(ModelMap map) {
        List<User> users = userService.selectAllUser();
        map.put("User", users);
        return "add";
    }

    //获取所有员工用于展示页面
    @RequestMapping("/addUser2.do")
    public String addUser2(ModelMap map) {
        List<User> users = userService.selectAllUser();
        map.put("User", users);
        return "userlist";
    }

    //新增员工到数据库
    @RequestMapping("/addUsertodb.do")
    public void add(User users, HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int i = userService.addUser(users);
        System.out.println("i的值是" + i);
        if (i > 0) {
            pw.write("<script type='text/javascript' >alert('新增成功');location='/selectAllUser.do';</script>");

        } else {
            pw.write("<script type='text/javascript' >alert('新增失败');</script>");
        }

//        return "forward:/selectAllUser.do";
    }


}