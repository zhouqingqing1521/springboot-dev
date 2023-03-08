package com.briup.springbootdev.web.vm;

import lombok.Data;

/**
 * 参数类主要解决问题： 将json字符串参数信息保存在对应的VM类中
 * @Author zqq
 * @Date 2022/10/31
 */
@Data
public class CategoryVM {
    private Integer id;
    private String name;
    private String description;
    private Integer no;
    private Integer parentId;
}
