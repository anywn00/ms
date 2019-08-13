package com.arno.miaoshao.redis.keys;

/**
 * @Author Arno
 * @ data 2019/8/12 11:02.
 */
public class UserKey extends BasePrefix {

    public UserKey(String token) {
        super(token);
    }
    UserKey(String key, int expireSeconds) {
        super(key, expireSeconds);
    }

    public static UserKey getById = new UserKey("id", 60);
    public static final UserKey userTokenKey = new UserKey("user_token",30 * 60);


}
