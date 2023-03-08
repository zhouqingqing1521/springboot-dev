package com.briup.springbootdev.web.controller;

import com.briup.springbootdev.bean.Article;
import com.briup.springbootdev.bean.Comment;
import com.briup.springbootdev.service.ICommentService;
import com.briup.springbootdev.utils.ObjectUtils;
import com.briup.springbootdev.utils.Result;
import com.briup.springbootdev.web.vm.CommentVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author zqq
 * @Date 2022/11/1
 */
@Api(tags = "评论模块")
@RestController
@RequestMapping("/auth/comment")
public class CommentController {
    @Autowired
    private ICommentService service;
    @ApiOperation("新增评论")
    @PostMapping
    public Result addComment(@RequestBody CommentVM vm){
        //1.将vm对象转换为bean对象 只复制了内容和时间属性
        Comment comment = ObjectUtils.vm2Bean(vm, Comment.class);
        //复制评论文章id
        comment.setArticle(Article.builder().id(vm.getArticleId()).build());
        //复制上级id
        if(vm.getParentId() != null){
            comment.setParent(Comment.builder().id(vm.getParentId()).build());
        }
        service.saveOrUpdateComment(comment);
        return Result.success();
    }
    @ApiOperation("修改评论信息")
    @PutMapping
    public Result updateComment(@RequestBody CommentVM vm){
        Comment comment = ObjectUtils.vm2Bean(vm, Comment.class);
        comment.setArticle(Article.builder().id(vm.getArticleId()).build());
        if(vm.getParentId() != null){
            comment.setParent(Comment.builder().id(vm.getParentId()).build());
        }
        service.saveOrUpdateComment(comment);
        return Result.success();
    }
    @ApiOperation(value = "批量删除评论id",notes = "删除效果：只删除该评论信息，对应子评论信息不会被删除，取消parent_id的值")
    @DeleteMapping
    public Result deleteBybatch(@RequestBody List<Integer> ids){
        service.deleteCommentInBatch(ids);
        return Result.success();
    }
    @GetMapping
    public Result findByPage(Integer pageNum,Integer pageSize){
        Page<Comment> page = service.findAll(pageNum, pageSize);
        return Result.success(page.getContent());
    }
}
