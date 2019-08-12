package com.arno.miaoshao.redis.keys;

/**
 * @Author Arno
 * @ data 2019/8/12 10:59.
 */
public interface KeyPrefix {

    String getKeyPrefix();


    int expireSeconds();
}
