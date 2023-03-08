package com.briup.springbootdev.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @Author:zqq
 * @Date:2023/3/8 12:52
 * 文章类 ：1.web service dao 传递数据 封装
 *       2.jpa 通过注解创建实体类对应的表
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter // vm-bean  Beanutils工具类  jpa查询
@Entity
@Table(name = "cms_article")
public class Article {

    //基本信息
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String content;
    @Column(name = "publish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")    //json 和时间属性值 转换时
    private Date publishTime;
    private Integer status;
    @Column(name = "read_times")
    private Integer readTimes;
    @Column(name = "thumb_up")
    private Integer thumbUp;
    @Column(name = "thumb_down")
    private Integer thumbDown;
    // 用户信息  fk  用户和咨询的关系 1：N
    @ManyToOne
    @JsonIgnore
    private User user;
    //目录信息  category_id fk   1:N
    @ManyToOne
    @JsonIgnore
    private Category category;

    //为了实现删除咨询时，可以级联删除评论信息，所以设置了文章类中评论属性
    @JsonIgnore //1个文章对应的是多个评论信息
    @OneToMany(mappedBy = "article",cascade = CascadeType.REMOVE)
    private List<Comment> comments;




    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category.getId() +
                '}';
    }
}
