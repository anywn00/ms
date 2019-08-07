package com.arno.miaoshao.service;

import com.arno.miaoshao.dao.UserDao;
import com.arno.miaoshao.domain.User;
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

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User user = new User();
        user.setId(2);
        user.setName("222");
        userDao.insert(user);

        User user1 = new User();
        user1.setId(1);
        user1.setName("111");
        userDao.insert(user);
        return true;
    }
}
