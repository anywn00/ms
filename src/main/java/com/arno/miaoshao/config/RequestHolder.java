package com.arno.miaoshao.config;

import com.arno.miaoshao.domain.MiaoshaUser;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *  请求的持有者
 * @Author Arno
 * @ data 2019/8/29 11:35.
 */
public class RequestHolder {

    private static final ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<MiaoshaUser> userThreadLocal = new ThreadLocal<>();


    public static void set(HttpServletRequest request) {
        requestThreadLocal.set(request);
    }


    public static HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    public static void set(MiaoshaUser user) {
        userThreadLocal.set(user);
    }


    public static MiaoshaUser getUser() {
        return userThreadLocal.get();
    }
}
