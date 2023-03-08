package com.briup.springbootdev.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * jpa 实体类对应的数据库表cms_user
 * @Author:zqq
 * @Date:2023/3/8 12:56
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cms_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true,nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String status;
    private String phone;
    private String realname;
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")//指定字符串转换为时间对象字符串格式
    private Date birthday;
    @Column(name = "register_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date registerTime;
    private String image;
    /*
      角色 与 用户 的关系 1： N
        设置一个外键表示role_id
     */
    @ManyToOne //默认效果： 自动生成外键名role_id 选择Role类中主键id
    private Role role;

    //为了实现删除用户时，希望删除调咨询信息及其对发表的评论信息
    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE) //1:N
    @JsonIgnore
    private List<Article> articles;

    @OneToMany(mappedBy = "user",cascade = CascadeType.REMOVE)  //1:N
    @JsonIgnore
    private List<Comment> comments;
}
