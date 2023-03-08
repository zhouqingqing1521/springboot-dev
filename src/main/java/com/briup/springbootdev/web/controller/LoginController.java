package com.briup.springbootdev.web.controller;

import com.briup.springbootdev.service.IUserService;
import com.briup.springbootdev.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author zqq
 * @Date 2022/10/27
 */
@Api(tags = "登录模块")
@RestController//前后端分离
public class LoginController {
    @Autowired
    private IUserService service;//实现代码解耦：


    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username",value = "用户名",required = true),
            @ApiImplicitParam(name = "password",value = "密码",required = true)
    })
    @PostMapping(value = "/login",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Result login(String username,String password){
        //调用service
        String token = service.login(username, password);
        return Result.success(token);

    }
   /* public Result login2(User user){
        return null;
    }*/

}
