package com.arno.miaoshao.config;

import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.redis.keys.UserKey;
import com.arno.miaoshao.service.RedisUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author arno
 * @date 2019-08-13 22:14
 */
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Autowired
    RedisUserService redisUserService;


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        String token = getCookieToken(request);
        if(token == null) {
            return null;
        }
        return redisUserService.getUserByToken(token);
    }

    private String getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if(StringUtils.equals(cookie.getName(), UserKey.userTokenKey.getKeyPrefix())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
