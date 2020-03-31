package com.wzj.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@Slf4j
public class AuthenctiationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");


        if (exception instanceof BadCredentialsException ||
                exception instanceof UsernameNotFoundException) {
            log.info("账户名或者密码输入错误!");
        } else if (exception instanceof LockedException) {
            log.info("账户被锁定，请联系管理员!");
        } else if (exception instanceof CredentialsExpiredException) {
            log.info("密码过期，请联系管理员!");
        } else if (exception instanceof AccountExpiredException) {
            log.info("账户过期，请联系管理员!");
        } else if (exception instanceof DisabledException) {
            log.info("账户被禁用，请联系管理员!");
        } else {
            log.info("登录失败!:{}",exception.getMessage());
        }

        response.sendRedirect("/login");
    }
}
