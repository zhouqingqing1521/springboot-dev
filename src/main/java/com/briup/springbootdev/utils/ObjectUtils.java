package com.briup.springbootdev.utils;

import org.springframework.beans.BeanUtils;

/**
 * 工具类
 * @Author zqq
 * @Date 2022/11/1
 */
public class ObjectUtils {
    private ObjectUtils(){}

    /**
     * 将vm对象转换为bean对象
     * @param vm  web接收到参数的封装对象
     * @param c   service中需要的bean类的字节码对象
     * @param <T>  任意bean包下类
     * @return  service中需要的bean对象
     */
    public static <T> T vm2Bean(Object vm,Class<T> c){
        //1.创建出service中需要的bean对象
        T target = null;
        try {
            target = c.newInstance();
            //2.将vm对象中属性值复制给target对象
            //属性名 和类型是相同
            BeanUtils.copyProperties(vm,target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //3.返回需要的bean对象
        return target;
    }
}
