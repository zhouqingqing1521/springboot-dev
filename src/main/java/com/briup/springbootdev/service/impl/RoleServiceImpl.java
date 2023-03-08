package com.briup.springbootdev.service.impl;

import com.briup.springbootdev.bean.Role;
import com.briup.springbootdev.dao.RoleDao;
import com.briup.springbootdev.exception.ServiceException;
import com.briup.springbootdev.service.IRoleService;
import com.briup.springbootdev.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service层实现类：是由项目经理提供的业务设计
 * @Author zqq
 * @Date 2022/10/25
 */
@Service//创建service层bean对象，调用类的方法
public class RoleServiceImpl implements IRoleService {
    //使用jpa实现dao层的功能
    @Autowired
    private RoleDao dao;

    public Page<Role> findAll(Integer pageNum, Integer pageSize) throws ServiceException {
        //jpa 默认第一页使用0表示 pageNum=5 :查询 5-1
        Page<Role> page = dao.findAll(PageRequest.of(pageNum-1, pageSize));
        return page;
    }

    public void saveOrUpdateRole(Role role) throws ServiceException {
        //新增和修改 放同一个方法中：

        //1.如果提交的角色名为空或空字符串，提示用户参数无效
        String name = role.getName();
        if(name == null || "".equals(name)){
           throw new ServiceException(ResultCode.PARAM_IS_INVALID);
        }
        //2.如果提交角色名与数据库中角色名相同，提示用户数据已存在
        Role roledb = dao.findByName(name);

        //当判断名字是否重复时，如果是修改的话，要求自己和自己的名称重复


        //新增时，判断查询到的用户不为null,再比较name
        if( roledb !=null && roledb.getId() != role.getId() && roledb.getName().equals(name)){
            throw new ServiceException(ResultCode.DATA_EXISTED);
        }


        //修改： id=1 name=admin  des=xxxxx
        //3.进行保存或更新
        dao.save(role);
    }
    public void deleteRoleInBatch( List<Integer> ids) throws ServiceException {

        ids.forEach(id -> dao.deleteById(id));



        //批量删除： 遍历执行
        //ids.forEach(id -> dao.deleteById(id)); //2
        //当执行该方法，不需要判断是否存在删除id
        //dao.deleteAllByIdInBatch(ids); //   1in (1,2,3,4)
        //当执行该方法，需要先进行逻辑判断是否存在删除id 否则用户提示错误
        //dao.deleteAllById(ids);//多次执行delete语句  3
        //扩展：事务操作：jpa 删除10条 当执行过程中出现异常sqlException
    }
}
