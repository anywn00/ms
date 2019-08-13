package com.arno.miaoshao.redis;

import com.arno.miaoshao.domain.User;
import com.arno.miaoshao.redis.keys.UserKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Arno
 * @ data 2019/8/13 17:36.
 */
@Service
public class RedisUserService {

    @Autowired
    private RedisUtil redisUtil;


    public void saveUser(String token, User user) {
        redisUtil.set(UserKey.userTokenKey,"" + token, user);
    }
}
