package com.arno.miaoshao.redis;

import com.alibaba.fastjson.JSON;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author arno
 * @date 2019-08-11 17:30
 */
@Service
public class RedisService {

    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private JedisPool jedisPool;


    public <T> T get(String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = jedis.get(key);
            T t = stringToBean(str, clazz);
            return t;
        }finally {
            returnJedis(jedis);
        }
    }


    public <T> boolean set(String key, T val){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(val);
            if(str == null) {
                return false;
            }
            jedis.set(key, str);
            return true;
        }finally {
            returnJedis(jedis);
        }
    }
    private <T> T stringToBean(String str, Class<T> aClass){
        if(str == null) {
            return null;
        }
        if(aClass == Integer.class || aClass == int.class){
            return (T) Integer.valueOf(str);
        }
        if(aClass == String.class) {
            return (T)str;
        }
        if(aClass == Long.class || aClass == long.class) {
            return (T) Long.valueOf(str);
        }
        return JSON.parseObject(str, aClass);
    }

    private <T> String beanToString(T t){
        if(t == null){
            return null;
        }
        Class<?> aClass = t.getClass();
        if(aClass == Integer.class || aClass == int.class){
            return "" + t;
        }
        if(aClass == String.class) {
            return (String)t;
        }
        if(aClass == Long.class || aClass == long.class) {
            return "" + t;
        }
        return JSON.toJSONString(t);
    }


    private void returnJedis(Jedis jedis){
        if(jedis != null){
            jedis.close();
        }
    }


    @Bean
    public JedisPool getJedisPool() {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxIdle(redisConfig.getPoorMaxIdle());
        config.setMaxTotal(redisConfig.getPoolMaxTotal());
        config.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);
        JedisPool pool = new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getTimeout() * 1000, redisConfig.getPassword());
        return pool;
    }
}