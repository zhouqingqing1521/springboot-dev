package com.briup.springbootdev.utils;

import java.util.Map;

/**
 * 可以设置和获取用户信息的工具类：
 * 可以实现在任意的位置获取用户信息 service
 * @Author zqq
 * @Date 2022/11/1
 */
public class UserInfoUtil {
    //每个线程（每个用户发送的请求）可以单独拥有一个副本存储自己的用户信息
    private static ThreadLocal<Map<String,Object>> threadLocal = new ThreadLocal<>();

    public static void setUserInfo(Map<String,Object> map){
        threadLocal.set(map);
    }
    public static Integer getUserId(){
        return (Integer) threadLocal.get().get("userId");
    }
}
