package com.briup.springbootdev.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @Author:zqq
 * @Date:2023/3/8 12:58
 * 评论类 自关联  先实现用户模块 实现咨询模块
 */
@Builder  //@Builder +无参 +全参 注解一起使用
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cms_comment")
public class Comment {

    //基本信息
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    private String time;
    @ManyToOne //父评论
    @JoinColumn(name = "parent_id") //设置外键名称，默认提供的名字 属性名_主键属性ming
    @JsonIgnore
    private Comment parent;
    /**
     * @OneToMany
     *  1.使用双向 注解 表示实体关系
     *  如果不使用 mappedBy 导致生成一张中间表，无需要使用中间表
     *  2. 使用双向注解 并指定了mappedBy 可以实现外键约束，不会生成中间表
     *  3. 使用双向注解。单独只使用 @JoinColumn
     *    注意：mappedBy 不能与 @JoinColumn
     *    每次只选择期中一个
     *
     * @ManyToOne : 使用自动生成的外键名
     */
    @OneToMany  //子评论
    @JoinColumn(name = "parent_id")
    private List<Comment> children;
    //用户信息 user_id
    @ManyToOne  //多个评论对应一个用户
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    //文章信息 article_id
    @ManyToOne //多个评论对应一个文章
    @JsonIgnore
    private Article article;
}
