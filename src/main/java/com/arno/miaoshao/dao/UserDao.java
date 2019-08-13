package com.arno.miaoshao.dao;

import com.arno.miaoshao.domain.MiaoshaUser;
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

    @Select("select * from miaosha_user where id = #{id}")
    MiaoshaUser getById(@Param("id") long id);

    @Insert("insert into miaosha_user(id,pick_name)values(#{id},#{pickName})")
    void insert( MiaoshaUser miaoshaUser);
}
