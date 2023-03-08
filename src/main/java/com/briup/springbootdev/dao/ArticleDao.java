package com.briup.springbootdev.dao;

import com.briup.springbootdev.bean.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author zqq
 * @Date 2022/11/1
 */
public interface ArticleDao extends JpaRepository<Article,Integer> {
    //自定义方法实现根据用户id查询咨询
    Page<Article> findByUserId(Integer userId,Pageable pageable);
}
