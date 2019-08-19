package com.arno.miaoshao.dao;

import com.arno.miaoshao.domain.MiaoshaOrder;
import com.arno.miaoshao.domain.OrderInfo;
import org.apache.ibatis.annotations.*;

/**
 * @Author arno
 * @date 2019-08-18 09:19
 */
@Mapper
public interface OrderDao {
    @Select("select * from order_info where user_id = #{userId} and goods_id = #{goodsId}")
    OrderInfo getByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);

    @Insert("insert into order_info (user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date) values(" +
            "#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    long insertOrder(OrderInfo orderInfo);


    @Insert("insert into miaosha_order (user_id,order_id,goods_id) values (#{userId},#{orderId},#{goodsId})")
    void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
