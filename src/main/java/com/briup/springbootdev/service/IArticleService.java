package com.briup.springbootdev.service;

import com.briup.springbootdev.bean.Article;
import com.briup.springbootdev.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IArticleService {
	/*
	  发布资讯或者编辑资讯
	  思考：
	  当用户新增咨询信息时，没有将用户id或用户名作为参数进行传递
	  利用ThreadLocal对象实现将获取用户的信息操作代码，编写在service层获取。
	 */
	void saveOrUpdateArticle(Article article)throws ServiceException;
	//分页获取当前用户的所有资讯
	Page<Article> findAllByUser(Integer pageNum,Integer pageSize)throws ServiceException;
	//批量删除资讯
	void deleteArticleInBatch(List<Integer> ids)throws ServiceException;
	//管理员审核资讯
	void updateArticleStatus(Integer id,String status)throws ServiceException;
	//根据分类id查找对应的资讯信息，并分页显示
	Page<Article> findAllByCategoryId(Integer articleId,int pageNum,int pageSize)throws ServiceException;
}
