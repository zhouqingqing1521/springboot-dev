package com.briup.springbootdev.service;

import com.briup.springbootdev.bean.Category;
import com.briup.springbootdev.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {
	//按序号升序查询分类信息，并进行分页
	Page<Category> findAllSortbyNo(Integer pageNum,Integer pageSize)throws ServiceException;
	//新增分类或者更新分类
	void saveOrUpdateCategory(Category category)throws ServiceException;
	//批量删除分类
	void deleteCategoryInBatch(List<Integer> ids)throws ServiceException;
	//更新分类序号
	void updateCategoryNo(Integer id,int no)throws ServiceException;
	
}
