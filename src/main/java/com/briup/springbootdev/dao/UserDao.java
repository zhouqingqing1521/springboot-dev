package com.briup.springbootdev.dao;

import com.briup.springbootdev.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @Author zqq
 * @Date 2022/10/27
 */
public interface UserDao extends JpaRepository<User,Integer> {

    //根据用户名查询用户信息
    User findByUsername(String username);//select * from table where username = ?1

    @Transactional
    @Modifying
    @Query("update User set status = ?2 where id = ?1") //HQL   SQL
    void updateStatusById(Integer id, String status);
}
