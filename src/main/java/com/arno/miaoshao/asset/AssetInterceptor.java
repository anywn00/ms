package com.arno.miaoshao.asset;

import com.alibaba.fastjson.JSON;
import com.arno.miaoshao.config.RequestHolder;
import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.exception.GlobalException;
import com.arno.miaoshao.redis.RedisUtil;
import com.arno.miaoshao.redis.keys.AccessKey;
import com.arno.miaoshao.redis.keys.BasePrefix;
import com.arno.miaoshao.redis.keys.UserKey;
import com.arno.miaoshao.result.CodeMsg;
import com.arno.miaoshao.result.Result;
import com.arno.miaoshao.service.RedisUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author Arno
 * @ data 2019/8/26 15:44.
 */
@Component
public class AssetInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUserService redisUserService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 将request 添加到 threadlocal中
        RequestHolder.set(request);
        //获取Miaouser
        MiaoshaUser user = getMiaoshaUserByRequest();
        //将Miaouser 添加到 threadlocal中
        if (user != null) {
            RequestHolder.set(user);
        }
        //TODO 这里判断是不是需要登陆
        HandlerMethod methodHandle = (HandlerMethod) handler;
        AccessLimit accessLimitnAnotation = methodHandle.getMethodAnnotation(AccessLimit.class);
        boolean require = accessLimitnAnotation.require();
        int seconds = accessLimitnAnotation.seconds();
        int maxCount = accessLimitnAnotation.maxCount();
        String key = request.getRequestURI();
        if(require) {
            if(user == null) {
                //跳转到登陆
                render(response, CodeMsg.SESSION_ERROR);
                return false;
            }
            key += "_"+ user.getId();
        }
        AccessKey accessKey = AccessKey.widthExpire(seconds);
        Integer count = redisUtil.get(accessKey, key, Integer.class);
        if(count == null) {
            redisUtil.set(accessKey,key, 1);
        } else if(count < maxCount){
            redisUtil.incr(accessKey,key);
        } else {
            render(response, CodeMsg.ACCESS_LIMIT_REPEAT);
            return false;
        }
        return true;
    }


    private void render(HttpServletResponse response, CodeMsg ms){
        response.setContentType("application/json;charset=UTF-8");
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            String str = JSON.toJSONString(Result.error(ms));
            outputStream.write(str.getBytes("UTF-8"));
            outputStream.flush();
        } catch (IOException e) {
              throw new GlobalException(CodeMsg.SERVER_500);
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                throw new GlobalException(CodeMsg.SERVER_500);
            }
        }
    }
    private MiaoshaUser getMiaoshaUserByRequest() {
        HttpServletRequest request = RequestHolder.getRequest();
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0) {
            return null;
        }
        String cookieName = UserKey.userTokenKey.getKeyPrefix();
        String token = null;
        for (Cookie cookie : cookies) {
            if (StringUtils.equals(cookie.getName(), cookieName)) {
                token = cookie.getValue();
            }
        }
        //从redis中取出user
        MiaoshaUser user = redisUserService.getUserByToken(token);
        //将user保存到 RequestHolder中
        return user;
    }
}
