package com.briup.springbootdev.service;

import com.briup.springbootdev.bean.Comment;
import com.briup.springbootdev.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICommentService {
	//分页获取所有评论
	Page<Comment> findAll(Integer pageNum, Integer pageSize) throws ServiceException;
	//新增评论或者更新评论
	void saveOrUpdateComment(Comment comment) throws ServiceException;
	//批量删除评论
	void deleteCommentInBatch(List<Integer> ids) throws ServiceException;
	//根据资讯id分页获取所有评论
	Page<Comment> findAllByArticleId(Integer articleId,Integer pageNum, Integer pageSize) throws ServiceException;

	

}
