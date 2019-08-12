package com.arno.miaoshao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author arno
 * @date 2019-08-12 22:32
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @RequestMapping("to_list")
    public String list(){
        return "goods_list";
    }
}
