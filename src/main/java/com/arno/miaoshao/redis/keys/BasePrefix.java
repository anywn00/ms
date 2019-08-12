package com.arno.miaoshao.redis.keys;

/**
 * @Author Arno
 * @ data 2019/8/12 11:01.
 */
public abstract class BasePrefix implements KeyPrefix{

    private String key;
    private int expireSeconds;

    BasePrefix(String key){
        //0代表永不过期
        this(key, 0);
    }

    BasePrefix(String key, int expireSeconds) {
        this.key = key;
        this.expireSeconds = expireSeconds;
    }

    @Override
    public String getKeyPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName + ":" + key;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(int expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

}
