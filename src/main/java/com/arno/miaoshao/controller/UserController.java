package com.arno.miaoshao.controller;

import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.result.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author arno
 * @date 2019-08-19 21:51
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("info")
    @ResponseBody
    public Result info(MiaoshaUser user) {
        return Result.success(user);
    }
}
