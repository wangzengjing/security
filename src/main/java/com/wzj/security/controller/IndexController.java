package com.wzj.security.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

@Controller
@Slf4j
public class IndexController {

    @RequestMapping("/users/view")
    public String user1(){

        return "user1";
    }

    @RequestMapping("/user2")
    public String user2(){
        return "user2";
    }

    @RequestMapping("/user3")
    public String user3(){
        return "user3";
    }

    @GetMapping("/login")
    public String login(){
        String username = "";
        //判断是否在会话中，不在会话中用户名为anonymousUser
        SecurityContext context = SecurityContextHolder.getContext();
        if(null != context) {
            Authentication authentication = context.getAuthentication();
            username = authentication.getName();
            Object principal = authentication.getPrincipal();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        }

        if(username.equals("anonymousUser")){
            return "login";
        }else{

            return "user1";
        }


    }




    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }


//    @RequestMapping("/logout")
//    public String logout(){
//        return "login";
//    }


}
