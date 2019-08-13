package com.arno.miaoshao.service;

import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.redis.RedisUtil;
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


    public void saveUser(String token, MiaoshaUser miaoshaUser) {
        redisUtil.set(UserKey.userTokenKey,token, miaoshaUser);
    }

    public MiaoshaUser getUserByToken(String token){
        if(token == null) {
            return null;
        }

        return redisUtil.get(UserKey.userTokenKey,token, MiaoshaUser.class);
    }
}
