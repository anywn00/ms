package com.arno.miaoshao.redis.keys;

/**
 * @Author Arno
 * @ data 2019/8/29 15:35.
 */
public class AccessKey extends BasePrefix {
    AccessKey(String key, int expireSeconds) {
        super(key, expireSeconds);
    }

    public static AccessKey widthExpire(int expireSeconds) {
        return new AccessKey("access", expireSeconds);
    }
}
