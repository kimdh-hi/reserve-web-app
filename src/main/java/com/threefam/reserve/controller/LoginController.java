package com.threefam.reserve.controller;

import com.threefam.reserve.dto.security.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "Login";
    }


    /**
     *
     *
     * oauth login이 잘 되는지 일단 테스트용 , responsbody로서 "ok"를 리턴하고 log.info를 활용하여 현재 인증된 사용자의 정보를 찍음.
     * 회원가입 폼이 완성 되면 없앨 예정!
     *
     */
    @GetMapping("/test")
    @ResponseBody
    public String oauthTest(@AuthenticationPrincipal PrincipalDetails details){

        log.info("Email = {} ",details.getUsername());
        log.info("password = {} ",details.getPassword());
        log.info("name = {} ",details.getName());

        return "ok";
    }
}
