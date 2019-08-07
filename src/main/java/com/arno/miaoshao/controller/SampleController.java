package com.arno.miaoshao.controller;

import com.arno.miaoshao.domain.User;
import com.arno.miaoshao.result.Result;
import com.arno.miaoshao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author arno
 * @date 2019-08-07 22:13
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    private UserService userService;
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> home() {
        return Result.success("Hello，world");
    }

    @RequestMapping("/hello/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name","arno");
        return "sys/hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User u = userService.getById(1);
        return Result.success(u);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        boolean bol = userService.tx();
        return Result.success(bol);
    }
}
