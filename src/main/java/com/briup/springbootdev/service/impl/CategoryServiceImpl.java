package com.briup.springbootdev.service.impl;

import com.briup.springbootdev.bean.Category;
import com.briup.springbootdev.dao.CategoryDao;
import com.briup.springbootdev.exception.ServiceException;
import com.briup.springbootdev.service.ICategoryService;
import com.briup.springbootdev.utils.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * service层对应的实现类
 * @Author zqq
 * @Date 2022/10/31
 */
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Autowired  //获取ioc容器中bean对象
    private CategoryDao dao;//springboot自动创建对象

    public Page<Category> findAllSortbyNo(Integer pageNum, Integer pageSize) throws ServiceException {
       // return dao.findAll(PageRequest.of(pageNum-1,pageSize, Sort.by("no")));//提供分页信息及排序规则
       //通过自定义sql语句实现只显示一级目录及其下面所有的子目录。
        Page<Category> page = dao.findByParentIdIsNull(PageRequest.of(pageNum - 1, pageSize, Sort.by("no")));
        return page;
    }
    //新增或修改操作
    public void saveOrUpdateCategory(Category category) throws ServiceException {
        // if(){}else{}

        //当修改操作时，判断是否存在在该栏目信息如果不存在抛出异常
        Integer id = category.getId();
        if( id != null){ //修改操作
            dao.findById(id) //当返回结果为null时，抛出异常
                    .orElseThrow(() ->
                            new ServiceException(ResultCode.DATA_NONE));
        }
        dao.save(category);
    }

    public void deleteCategoryInBatch(List<Integer> ids) throws ServiceException {
        /*
           实现效果：
           利用jpa级联操作：当删除一个栏目时，对应的子栏目要求必须也删除。保留栏目对应咨询信息
         */
        ids.forEach(id -> dao.deleteById(id));
    }

    public void updateCategoryNo(Integer id, int no) throws ServiceException {
        //使用自定义的HQL语句实现操作 update
        dao.updateNoById(id,no);
    }
}
