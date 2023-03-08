package com.briup.springbootdev.dao;

import com.briup.springbootdev.bean.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * @Author zqq
 * @Date 2022/10/31
 */
//springboot-jpa，实现自动生成该接口的实现类对象，不需要注解
public interface CategoryDao extends JpaRepository<Category,Integer> {
    //带来提供好的方法实现基本的curd操作
    /*
        select * from cms_category where parent_id is null

     */
    Page<Category> findByParentIdIsNull(Pageable page);

    @Transactional
    @Modifying
    @Query("update Category set no = ?2 where id = ?1")
    void updateNoById(Integer id, int no);
}
