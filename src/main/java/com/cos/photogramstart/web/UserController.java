package com.cos.photogramstart.web;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    @GetMapping("/user/{id}")
    public String profile(@PathVariable("id") int id){
        return "user/profile";
    }

    @GetMapping("/user/{id}/update")
    public String update(@PathVariable("id") int id,
                         @AuthenticationPrincipal PrincipalDetails principalDetails){

        //1.추천
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //2. 극혐
        PrincipalDetails mPrincipalDetails = (PrincipalDetails) auth.getPrincipal();

        return "user/update";
    }
}
