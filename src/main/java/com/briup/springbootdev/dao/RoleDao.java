package com.briup.springbootdev.dao;

import com.briup.springbootdev.bean.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 使用jpa提供的一个泛型接口实现数据的操作
 * @Author zqq
 * @Date 2022/10/25
 */
//不加任何注解 创建bean对象
public interface RoleDao extends JpaRepository<Role,Integer> {
    Role findByName(String name);
    /*
        不需要实现该接口。jpa实现。

        当默认提供的方法无法解决数据库操作sql .需要自定义sql或方法

     */
}
