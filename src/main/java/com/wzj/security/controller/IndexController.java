package com.wzj.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
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

    @GetMapping("/login_p")
    public String login(){
        return "login";
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
