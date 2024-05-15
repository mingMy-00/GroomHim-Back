package com.care.GroomHimBack.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Security;
import java.util.Collection;
import java.util.Iterator;

@ResponseBody
@Controller
public class MainController {
    @GetMapping("/")
    public String mainP() {

        //사용자 이름
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //iterator을 통해서 반복하며 회원 role값 뽑기
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        return "Main Controller" + username + role;

    }
}
