package com.cos.photogramstart.web;

import org.hibernate.annotations.GeneratorType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/auth/signin")
    public String signinForm(){
        return "auth/signin";
    }

    @GetMapping("/auth/signup")
    public String signupForm(){
        return "auth/signup";
    }

    //회원가입 버튼 -> /auth/signup -> /auth/sigin
    @PostMapping("/auth/signup")
    public String signup(){
        System.out.println("signup 실행됨?");
        return "auth/signin";
    }
}
