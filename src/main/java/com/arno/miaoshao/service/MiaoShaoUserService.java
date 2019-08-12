package com.arno.miaoshao.service;

import com.arno.miaoshao.dao.UserDao;
import com.arno.miaoshao.domain.User;
import com.arno.miaoshao.exception.GlobalException;
import com.arno.miaoshao.result.CodeMsg;
import com.arno.miaoshao.util.Md5Util;
import com.arno.miaoshao.util.UUIDUtil;
import com.arno.miaoshao.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author arno
 * @date 2019-08-12 22:00
 */
@Service
public class MiaoShaoUserService {
    @Autowired
    private UserDao userDao;


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
        User user = userDao.getById(Long.parseLong(mobile));
        if(user == null) {
            //手机号码不存在
            throw new GlobalException(CodeMsg.MOBILE_EXIST);
        }

        String dbPassword = user.getPassword();
        String md5Password = Md5Util.inputPassToDbPass(password, user.getSalt());
        if(!StringUtils.equals(md5Password, dbPassword)) {
            //密码错误
            throw new GlobalException(CodeMsg.PASSWORD_EXIST);
        }

        //登录成功
        //生成token
        String token = UUIDUtil.getId();
        //设置cokie
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return token;
    }




}
