package com.briup.springbootdev.web.vm;

import lombok.Data;

/**
 * 接收前端发送json字符串，封装成指定的一个对象
 * @Author zqq
 * @Date 2022/11/1
 */
@Data
public class CommentVM {
    private Integer id;//可选项 新增不需要 修改添加
    private String content;
    private String time;
    private Integer articleId;
    private Integer parentId;//可选项  评论文章不需要， 回复评论 提供参数值
}
