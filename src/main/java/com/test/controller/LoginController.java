/**
 * Copyright (C), 2018-2018, 中兴微品
 * FileName: LoginController
 * Author:   Administrator
 * Date:     2018/7/25 12:36
 * Description: LoginController
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.test.controller;

import com.test.po.Usermanger;
import com.test.service.UserMangerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 〈LoginController〉
 *
 * @author Administrator
 * @create 2018/7/25
 * @since 1.0.0
 */
@Controller
public class LoginController {
    @Autowired
    private UserMangerService userMangerService;
    @RequestMapping("login.do")
    public String login(Usermanger usermanger){
        Boolean result = userMangerService.selectByPrimaryKey(usermanger.getUsername(), usermanger.getPassword());
        if (result){

            return "redirect:selectAll.do";
        }else {
            return "forward:login.jsp";
        }
    }
}
