package com.arno.miaoshao.controller;

import com.arno.miaoshao.result.Result;
import com.arno.miaoshao.service.MiaoShaUserService;
import com.arno.miaoshao.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Arno
 * @ data 2019/8/12 14:47.
 */
@Controller
@RequestMapping("/login")
public class LogInController {

    @Autowired
    private MiaoShaUserService miaoShaoUserService;
    @RequestMapping("to_login")
    public String toLogIn() {
        return "to_login";
    }


    @RequestMapping("do_login")
    @ResponseBody
    public Result<String> doLogin(UserVo userVo, HttpServletResponse response, HttpServletRequest request) {
        String token = miaoShaoUserService.doLogin(userVo,response);
        return Result.success(token);
    }




}
