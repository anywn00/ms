package com.arno.miaoshao.service;

import com.arno.miaoshao.dao.OrderDao;
import com.arno.miaoshao.domain.MiaoshaOrder;
import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.domain.OrderInfo;
import com.arno.miaoshao.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author arno
 * @date 2019-08-18 09:17
 */
@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    public OrderInfo getByUserIdGoodsId(Long userId, long goodsId) {
        return orderDao.getByUserIdGoodsId(userId,goodsId);
    }

    @Transactional
    public OrderInfo createOrder(MiaoshaUser miaoshaUser, GoodsVo goodsVo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setDeliveryAddrId(0l);
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsCount(goodsVo.getStockCount());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        orderInfo.setUserId(miaoshaUser.getId());

        long orderId = orderDao.insertOrder(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();

        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(miaoshaUser.getId());
        orderDao.insertMiaoshaOrder(miaoshaOrder);
        return orderInfo;
    }
}
