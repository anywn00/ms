package com.arno.miaoshao.service;

import com.arno.miaoshao.dao.UserDao;
import com.arno.miaoshao.domain.MiaoshaUser;
import com.arno.miaoshao.exception.GlobalException;
import com.arno.miaoshao.redis.keys.UserKey;
import com.arno.miaoshao.result.CodeMsg;
import com.arno.miaoshao.util.Md5Util;
import com.arno.miaoshao.util.UUIDUtil;
import com.arno.miaoshao.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author arno
 * @date 2019-08-12 22:00
 */
@Service
public class MiaoShaUserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisUserService redisUserService;


    public String doLogin(UserVo userVo, HttpServletResponse response) {
        if(userVo == null) {
            throw new GlobalException(CodeMsg.PARAM_ERROR);
        }
        String mobile = userVo.getMobile();
        String password = userVo.getPassword();

        if(StringUtils.isBlank(mobile)) {
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        if(StringUtils.isBlank(password)) {
            throw new GlobalException(CodeMsg.PASSWORD_EMPTY);
        }
        MiaoshaUser miaoshaUser = userDao.getById(Long.parseLong(mobile));
        if(miaoshaUser == null) {
            //手机号码不存在
            throw new GlobalException(CodeMsg.MOBILE_EXIST);
        }

        String dbPassword = miaoshaUser.getPassword();
        String md5Password = Md5Util.inputPassToDbPass(password, miaoshaUser.getSalt());
        if(!StringUtils.equals(md5Password, dbPassword)) {
            //密码错误
            throw new GlobalException(CodeMsg.PASSWORD_EXIST);
        }

        //登录成功
        //生成token
        String token = UUIDUtil.getId();

        //设置cookie
        Cookie cookie = new Cookie(UserKey.userTokenKey.getKeyPrefix(), token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(UserKey.userTokenKey.getExpireSeconds());
        response.addCookie(cookie);

        //将用户信息 存储到redis
        redisUserService.saveUser(token, miaoshaUser);
        return token;
    }




}
