package com.briup.springbootdev.web.controller;

import com.briup.springbootdev.bean.Category;
import com.briup.springbootdev.service.ICategoryService;
import com.briup.springbootdev.utils.Result;
import com.briup.springbootdev.web.vm.CategoryVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 栏目模块
 * @Author zqq
 * @Date 2022/10/31
 */
@Api(tags = "栏目模块")
@RestController// json数据的交互
@RequestMapping("/auth/category") //统一路径前缀表示同一个模块下的功能
public class CategoryController {
    @Autowired//自动装配：ioc容器中的service对象
    private ICategoryService service;
    /*
        根据设计的接口文档，实现对应的方法
        当接口文档中设计的json字符串无法直接对象bean类对象
        通过自定义参数类表示接收的信息
     */
    @ApiOperation("新增或修改目录信息")
    @PostMapping
    public Result saveOrUpdate(@RequestBody CategoryVM vm){
        System.out.println(vm);
        //将vm对象转换成bean对象
        service.saveOrUpdateCategory(vm2Bean(vm));
        return Result.success();
    }

    @ApiOperation("按照序号升序分页查询目录信息")
    @GetMapping("/page")
    public Result findByPage(Integer pageNum,Integer pageSize){
        Page<Category> page = service.findAllSortbyNo(pageNum, pageSize);
        return Result.success(page);
    }

    @ApiOperation(value = "批量删除目录信息",notes = "删除栏目时，删除对应的子栏目,然后将对应的咨询信息的外键值设置为null")
    @DeleteMapping
    public Result deleteByBatch(@RequestBody List<Integer> ids){
        service.deleteCategoryInBatch(ids);
        return Result.success();
    }

    @ApiOperation("更新目录序号信息")
    @GetMapping("/no")
    public Result updateNoById(Integer id,Integer no){
        service.updateCategoryNo(id,no);
        return Result.success();
    }

    /**
     * 将VM对象转换成bean
     * @param vm
     * @return
     */
    private Category vm2Bean(CategoryVM vm){
        Category category = new Category();
        //通过工具类实现对象属性复制，要求属性名是相同的。
        BeanUtils.copyProperties(vm,category);
        /*
            当属性名不相同时，手动创建对象进行属性赋值
            VM类：  Integer parentId
            bean类：  Category parent;
         */
        //表示修改目录或新增二级目录，才会设置父级Id
        if(vm.getParentId() != null){
            Category parent = new Category();
            parent.setId(vm.getParentId());

            //手动设置category属性值 parent信息
            category.setParent(parent);
        }
        return category;
    }
}
