package com.briup.springbootdev.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 目录类型  一级目录 二级目录 ....
 *  * 上级目录  父目录  子目录 下级目录
 *  * 特点： 自关联的特点 ：将对应关系的注解信息定义在同一张表中。
 * @Author:zqq
 * @Date:2023/3/8 13:00
 */
@Getter
@Setter
@Entity
@Table(name = "cms_category")
public class Category {

    @Id //pk约束
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false) //nn 约束
    private String name;
    private String description;
    private Integer no;
    //CascadeType.REMOVE 删除父级栏目时，对应的子栏目信息一起删除
    @OneToMany(mappedBy = "parent",cascade = CascadeType.REMOVE)
    private List<Category> children;//1对多

    @ManyToOne
    @JoinColumn(name="parent_id")
    @JsonIgnore //转换json字符串时，不进行序列化
    private Category parent;


    //为了实现删除栏目时，将对应的咨询信息使用的外键值 set null
    @OneToMany //1个栏目多个咨询信息
    @JoinColumn(name = "category_id")
    private List<Article> articles;
}
