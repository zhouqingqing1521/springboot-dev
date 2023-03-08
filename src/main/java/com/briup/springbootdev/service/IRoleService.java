package com.briup.springbootdev.service;

import com.briup.springbootdev.bean.Role;
import com.briup.springbootdev.exception.ServiceException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {
	//分页获取所有角色信息
	Page<Role> findAll(Integer pageNum,Integer pageSize)throws ServiceException;
	//新增角色信息或者更新角色信息
	void saveOrUpdateRole(Role role)throws ServiceException;
	//批量删除角色信息
	void deleteRoleInBatch(List<Integer> ids)throws ServiceException;
}
