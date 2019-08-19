package com.arno.miaoshao.controller;

import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.domain.OrderInfo;
import com.arno.miaoshao.result.CodeMsg;
import com.arno.miaoshao.service.GoodsService;
import com.arno.miaoshao.service.MiaoshaService;
import com.arno.miaoshao.service.OrderService;
import com.arno.miaoshao.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author arno
 * @date 2019-08-18 08:59
 */
@Controller
@RequestMapping("miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaService miaoshaService;
    @RequestMapping("do_miaosha")
    public String doMaiosha(MiaoshaUser miaoshaUser,Model model, @RequestParam("goodsId") long goodsId) {
        GoodsVo goodsVo = goodsService.getById(goodsId);
        int stock = goodsVo.getGoodsStock();
        //判断库存
        if(stock == 0) {
            model.addAttribute("errorMsg", CodeMsg.GOODS_STOCK_NUM.getMsg());
            return "error";
        }
        //判断是不是秒杀过了 不能重复秒杀
        OrderInfo orderInfo = orderService.getByUserIdGoodsId(miaoshaUser.getId(),goodsId);
        if(orderInfo != null) {
            model.addAttribute("errorMsg", CodeMsg.GOODS_NOT_REPEAT.getMsg());
            return "error";
        }
        //生成订单 减少库存
        orderInfo = miaoshaService.reduceStockCreateOrder(miaoshaUser,goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goodsVo",goodsVo);
        return "order_detail";

    }
}
