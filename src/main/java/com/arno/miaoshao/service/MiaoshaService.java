package com.arno.miaoshao.service;

import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.domain.OrderInfo;
import com.arno.miaoshao.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author arno
 * @date 2019-08-18 09:17
 */
@Service
public class MiaoshaService {

    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @Transactional
    public OrderInfo reduceStockCreateOrder(MiaoshaUser miaoshaUser, GoodsVo goodsVo) {
        //减库存
        goodsService.reduceStock(goodsVo);

        //生成订单
        OrderInfo orderInfo = orderService.createOrder(miaoshaUser, goodsVo);
        return orderInfo;
    }
}
