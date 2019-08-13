package com.arno.miaoshao.service;

import com.arno.miaoshao.dao.UserDao;
import com.arno.miaoshao.domain.MiaoshaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author arno
 * @date 2019-08-07 22:12
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public MiaoshaUser getById(long id){
        return userDao.getById(id);
    }



    @Transactional
    public boolean tx() {
        MiaoshaUser miaoshaUser = new MiaoshaUser();
        miaoshaUser.setId(2);
        miaoshaUser.setNickname("222");
        userDao.insert(miaoshaUser);

        MiaoshaUser miaoshaUser1 = new MiaoshaUser();
        miaoshaUser1.setId(1);
        miaoshaUser1.setNickname("111");
        userDao.insert(miaoshaUser);
        return true;
    }
}
