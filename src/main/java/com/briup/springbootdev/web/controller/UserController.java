package com.briup.springbootdev.web.controller;

import com.briup.springbootdev.bean.User;
import com.briup.springbootdev.service.IUserService;
import com.briup.springbootdev.utils.JwtUtil;
import com.briup.springbootdev.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户管理模块
 * @Author zqq
 * @Date 2022/10/27
 */
@Api(tags = "用户管理模块")
@RestController
@RequestMapping("/auth/user")
public class UserController {
    @Autowired
    private IUserService service;


    @ApiOperation("新增用户")
    @PostMapping
    public Result addUser(@RequestBody User user){
        service.saveOrUpdateUser(user);
        return Result.success();
    }
    @ApiOperation("修改用户")
    @PutMapping
    public Result updateUser(@RequestBody User user){
        service.saveOrUpdateUser(user);
        return Result.success();
    }
    @ApiOperation("分页查询用户信息")
    @GetMapping
    public Result findByPage(Integer pageNum,Integer pageSize){
        Page<User> page = service.getAll(pageNum, pageSize);
        return Result.success(page);
    }
    @ApiOperation("批量删除用户")
    @DeleteMapping
    public Result deleteBybatch(@RequestBody List<Integer> ids){
        service.deleteUserInBatch(ids);
        return Result.success();
    }
    @ApiOperation("获取当前登录用户信息")
    @GetMapping("/current")
    public Result findCurrentUser(@ApiIgnore @RequestHeader String token){
        String username = JwtUtil.getUserId(token);
        User user = service.findUserByUsername(username);
        return Result.success(user);
    }
    @ApiOperation("更新用户状态")
    @GetMapping("/status")
    public Result updateStatusById(Integer id,String status){
        service.updateUserStatus(id,status);
        return Result.success();
    }
    @ApiOperation("当参数为请求对象时，swagger是否显示参数信息")
    @GetMapping("/test")
    public Result findUser(HttpServletRequest request){
        String token = request.getHeader("token");
        User u = service.findUserByUsername(JwtUtil.getUserId(token));
        return Result.success(u);
    }
}
