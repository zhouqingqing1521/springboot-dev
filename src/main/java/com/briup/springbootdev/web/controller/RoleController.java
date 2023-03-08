package com.briup.springbootdev.web.controller;

import com.briup.springbootdev.bean.Role;
import com.briup.springbootdev.service.IRoleService;
import com.briup.springbootdev.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 权限管理模块：基于RESTful风格设计的接口
 * 参考我们提供的设计文档： 接口文档
 * https://console-docs.apipost.cn/preview/23048934a4b367f3/bfb55bba8fbe497d
 *
 * 为了测试当前的接口是否能实现文档设计的功接口要求，利用swagger进行操作
 * 单独swagger
 * swagger+jwt
 * 扩展：
 * 每次修改代码后，必须手动重启项目，实现代码更新
 * 实现代码的热部署
 *    1.工具：idea 设置项目运行时，实现热部署ctrl+shift+alt+/
 *    2.项目:框架  devtools
 *    3.设置触发进行热部署的场景：
 *      通过设置主类
 *    4.缺点：项目写一半，编译错误，自动热部署
 * springboot项目热部署
 * @Author zqq
 * @Date 2022/10/25
 */
@Api(tags = "角色管理")
@RestController
@RequestMapping("/auth/role")
public class RoleController {
    @Autowired
    private IRoleService service;
    //新增或修改
    @ApiOperation("新增或修改角色信息")
    @PostMapping
    public Result addOrUpdateRole(@RequestBody Role role){
        //调用service层方式实现功能..
        service.saveOrUpdateRole(role);
        return Result.success();
    }
    //分页查询
    @ApiOperation("查询角色（分页）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "当前页码",required = true,defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小",required = true,defaultValue = "10"),
    })
    @GetMapping
    public Result findByPage(@RequestParam("pageNum") Integer pageNum,Integer pageSize){
        Page<Role> page = service.findAll(pageNum, pageSize);
        return Result.success(page.getContent());
    }
    //批量删除
    //根据接口文档定义的内容，创建对应的方法
    /*@ApiOperation("批量删除")
    @DeleteMapping("/batch")
    public Result deletByBatch(@RequestParam("id") List<Integer> ids){
        service.deleteRoleInBatch(ids);
        return Result.success();
    }*/
    @ApiOperation(value = "批量删除",notes = "删除角色id时，不能删除对应用户信息，只能实现取消用户角色信息 set null")
    @DeleteMapping("/batch")
    public Result deletByBatch(Integer[] ids){
        service.deleteRoleInBatch(Arrays.asList(ids));
        return Result.success();
    }
}
