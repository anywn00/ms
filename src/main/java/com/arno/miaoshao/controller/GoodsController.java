package com.arno.miaoshao.controller;

import com.arno.miaoshao.domain.MiaoshaUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * @Author arno
 * @date 2019-08-12 22:32
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @RequestMapping("to_list")
    public String list(MiaoshaUser miaoshaUser, Model model){
        if(miaoshaUser == null) {
            return "to_login";
        }
        model.addAttribute("user", miaoshaUser);
        return "goods_list";
    }


}
