package com.arno.miaoshao.dao;

import com.arno.miaoshao.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Author arno
 * @date 2019-08-07 22:10
 */
@Mapper
public interface UserDao {

    @Select("select * from user where id = #{id}")
    User getById(@Param("id") int id);

    @Insert("insert into user(id,name)values(#{id},#{name})")
    void insert( User user);
}
