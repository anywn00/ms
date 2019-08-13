package com.arno.miaoshao.redis;

import com.alibaba.fastjson.JSON;
import com.arno.miaoshao.redis.keys.KeyPrefix;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Author arno
 * @date 2019-08-11 17:30
 */
@Repository
public class RedisUtil {

    @Autowired
    private RedisConfig redisConfig;
    @Autowired
    private JedisPool jedisPool;


    public <T> T get(KeyPrefix keyPrefix, String key, Class<T> clazz){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            key = keyPrefix.getKeyPrefix() + ":" + key;
            String str = jedis.get(key);
            T t = stringToBean(str, clazz);
            return t;
        }finally {
            returnJedis(jedis);
        }
    }


    public <T> boolean set(KeyPrefix keyPrefix,String key, T val){
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            String str = beanToString(val);
            if(str == null) {
                return false;
            }
            key = keyPrefix.getKeyPrefix() + ":" + key;
            int ex = keyPrefix.expireSeconds();
            if(ex <= 0) {
                jedis.set(key, str);
            } else {
                jedis.setex(key,keyPrefix.expireSeconds(), str);
            }

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
