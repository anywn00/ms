package com.arno.miaoshao.controller;

import com.arno.miaoshao.domain.User;
import com.arno.miaoshao.service.UserService;
import com.arno.miaoshao.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author Arno
 * @ data 2019/8/12 14:47.
 */
@Controller
@RequestMapping("/login")
public class LogInController {

    @Autowired
    private UserService userService;
    @RequestMapping("to_login")
    public String toLogIn() {
        return "to_login";
    }


    @RequestMapping("do_login")
    public void doLogin(UserVo userVo) {
        System.out.println(userVo.toString());
        User user = userService.getByMobile(userVo.getMobile());

    }




}
