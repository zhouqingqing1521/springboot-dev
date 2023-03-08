package com.briup.springbootdev.service;

import com.briup.springbootdev.bean.User;
import com.briup.springbootdev.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
	//分页获取所有用户信息
	Page<User> getAll(Integer pageNum,Integer pageSize)throws ServiceException;
	//新增用户信息或者更新信息
	void saveOrUpdateUser(User user)throws ServiceException;
	//批量删除用户信息
	void deleteUserInBatch(List<Integer> ids)throws ServiceException;
	//修改用户的状态
	void updateUserStatus(Integer id,String status)throws ServiceException;
	//登录,登录成功，返回token字符串
	String login(String username,String password)throws ServiceException;
	//根据用户名获取用户信息
	User findUserByUsername(String username)throws ServiceException;
}
