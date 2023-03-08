package com.briup.springbootdev.service.impl;

import com.briup.springbootdev.bean.Article;
import com.briup.springbootdev.bean.User;
import com.briup.springbootdev.config.CmsInfo;
import com.briup.springbootdev.dao.ArticleDao;
import com.briup.springbootdev.exception.ServiceException;
import com.briup.springbootdev.service.IArticleService;
import com.briup.springbootdev.utils.UserInfoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author zqq
 * @Date 2022/11/1
 */
@Service
public class ArticleServiceImpl implements IArticleService {
    @Autowired
    private ArticleDao dao;

    public void saveOrUpdateArticle(Article article) throws ServiceException {
        //1.使用工具类获取同一个线程中的保存的用户信息
        Integer userId = UserInfoUtil.getUserId();
        System.out.println("发布咨询的用户id:"+userId);

        if(article.getId() == null){//新增
            //新增：
            article.setPublishTime(new Date());//发布时间
            article.setStatus(CmsInfo.ARTICLE_STATUS_INIT);//文章初始状态，未审核
            article.setReadTimes(0);
            article.setThumbUp(0);
            article.setThumbDown(0);
            User user = new User();
            user.setId(userId);
            article.setUser(user);
        }else {
            //修改时，可以提供逻辑验证修改后的数据
        }
        dao.save(article);
    }

    public Page<Article> findAllByUser(Integer pageNum, Integer pageSize) throws ServiceException {
        Integer userId = UserInfoUtil.getUserId();
        Page<Article> page = dao.findByUserId(userId, PageRequest.of(pageNum - 1, pageSize));
        return page;
    }

    public void deleteArticleInBatch(List<Integer> ids) throws ServiceException {
        /*
            批量删除咨询信息，咨询信息的主键值，被用在了评论信息的中作为外键值
         */
        for (Integer id : ids) {
            dao.deleteById(id);
        }
        //dao.deleteAllById(ids);


        //使用批量删除语句，需要手动set null 排除外键约束
        //dao.deleteAllByIdInBatch(ids);
    }

    public void updateArticleStatus(Integer id, String status) throws ServiceException {

    }

    public Page<Article> findAllByCategoryId(Integer articleId, int pageNum, int pageSize) throws ServiceException {
        return null;
    }
}
