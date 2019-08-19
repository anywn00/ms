package com.arno.miaoshao.controller;

import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.service.GoodsService;
import com.arno.miaoshao.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.List;


/**
 * @Author arno
 * @date 2019-08-12 22:32
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;
    @RequestMapping("to_list")
    public String list(MiaoshaUser miaoshaUser, Model model){
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        model.addAttribute("goodsVoList", goodsVoList);
        return "goods_list";
    }

    @RequestMapping("to_detail/{id}")
    public String goodsDetail(MiaoshaUser miaoshaUser,Model model,
                              @PathVariable("id") long goodsId) {
        GoodsVo goodsVo = goodsService.getById(goodsId);
        model.addAttribute("goods",goodsVo);
        model.addAttribute("miaoshaUser",miaoshaUser);

        long startTime = goodsVo.getStartDate().getTime();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String a = formatter.format(goodsVo.getStartDate());

        long endTime = goodsVo.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int status = 0;
        int remainTime = 0;
        if(now < startTime) { //秒杀未开始
            status = 0;
            remainTime = (int)(startTime - now) / 1000;
        } else if(now > endTime) { //秒杀结束
            status = -1;
            remainTime = -1;
        }else{
            status = 1;
            remainTime = 0;
        }
        model.addAttribute("status", status);
        model.addAttribute("remainTime", remainTime);
        return "goods_detail";
    }



}
