package com.wzj.security.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class AuthenctiationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");

//        RequestCache cache = new HttpSessionRequestCache();
//        SavedRequest savedRequest = cache.getRequest(request, response);
//        // 如果来源请求为空则跳转到用户博客首页
//        String url = "";
//        if((savedRequest==null)){
//            url = "/blog/"+ SecurityUtil.getLoginUser();
//        }else{
//            url = savedRequest.getRedirectUrl();
//        }
//
//        System.out.println(url);
        System.out.println("登录成功，转发url");
        response.sendRedirect("/users");


    }
}
