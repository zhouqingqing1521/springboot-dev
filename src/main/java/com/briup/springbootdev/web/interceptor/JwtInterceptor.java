package com.briup.springbootdev.web.interceptor;

import com.briup.springbootdev.utils.JwtUtil;
import com.briup.springbootdev.utils.UserInfoUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 1.对用户在/auth/**提交的token信息进行验证
 * @Author zqq
 * @Date 2022/10/27
 */
@Component//创建一个拦截器对象
public class JwtInterceptor implements HandlerInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取用户的请求头信息：token
        String token = request.getHeader("token");

        //2.当用户未提供时，web提示用户token无效，请登录
        if(token == null){
            //抛出指定异常类型
            throw new RuntimeException("token无效，请重新登录");
        }
        //3.当用户提供了token字符串，但无效的：超时(长时间登录)，不合法的字符串(非系统用户)
        JwtUtil.checkSign(token);

        //4.当通过jwt验证后，需要把用户的信息保存在ThreadLocal中
        UserInfoUtil.setUserInfo(JwtUtil.getInfo(token));
        return true;
    }
}
