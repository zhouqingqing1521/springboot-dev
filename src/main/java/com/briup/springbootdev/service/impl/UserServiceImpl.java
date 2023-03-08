package com.briup.springbootdev.service.impl;

import com.briup.springbootdev.bean.Role;
import com.briup.springbootdev.bean.User;
import com.briup.springbootdev.config.CmsInfo;
import com.briup.springbootdev.dao.RoleDao;
import com.briup.springbootdev.dao.UserDao;
import com.briup.springbootdev.exception.ServiceException;
import com.briup.springbootdev.service.IUserService;
import com.briup.springbootdev.utils.JwtUtil;
import com.briup.springbootdev.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**1.为实现类创建对应的对象： spring bean对象
 * @Author zqq
 * @Date 2022/10/27
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserDao dao;//获取spring自动创建dao层对象
    @Autowired
    private RoleDao roleDao;

    public Page<User> getAll(Integer pageNum, Integer pageSize) throws ServiceException {
        return dao.findAll(PageRequest.of(pageNum-1,pageSize));
    }

    public void saveOrUpdateUser(User user) throws ServiceException {
        //逻辑1;是否存在该角色添加用户信息
        Role role = roleDao.findById(user.getRole().getId()).orElse(null);
        if(role == null){
            throw new RuntimeException("角色不存在");
        }
        //添加逻辑主要对数据是否合法验证....
        // 补充：对添加的数据库中用户名有唯一约束，必须代码判断并返回具体的逻辑

        if(user.getId() == null){//新增
            user.setRegisterTime(new Date());
            user.setStatus(CmsInfo.USER_STATUS_YES);
        }
        dao.save(user);
    }

    public void deleteUserInBatch(List<Integer> ids) throws ServiceException {
        for (Integer id : ids) {
            dao.deleteById(id);
        }
    }

    public void updateUserStatus(Integer id, String status) throws ServiceException {
        //自定义HQL语句实现更新状态
        dao.updateStatusById(id, status);
    }

    public String login(String username, String password) throws ServiceException {
        //2.方法失败：通过异常对象表示失败的原因，返回给web层--->浏览器
        User user = dao.findByUsername(username);

        if(user == null){//表示用户不存在
            //为了防止用户暴力破解提供统一的信息
            throw new ServiceException(ResultCode.USER_LOGIN_ERROR);
        }

        if(!password.equals(user.getPassword())){//密码错误
            throw new ServiceException(ResultCode.USER_LOGIN_ERROR);
        }

        //账号状态问题：被禁用的账号，无法进行登录
        if(CmsInfo.USER_STATUS_NO.equals(user.getStatus())){
            throw new ServiceException(ResultCode.USER_ACCOUNT_FORBIDDEN);
        }

        //1.方法执行成功，返回需要的token字符串
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId",user.getId());
        //put.... 根据实际情况进行设置，
        String token = JwtUtil.sign(username, userInfo);
        return token;
    }

    public User findUserByUsername(String username) throws ServiceException {
        User user = dao.findByUsername(username);
        return user;
    }
}
