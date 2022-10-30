package com.zerobase.bakingin_project.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping("/")
    public String index () {
        return "index";
    }

    @GetMapping("/error/denied") // 관리자 권한 없는 경우 관리자 페이지 접속 시 발생하는 에러 페이지
    public String error() {
        return "error/denied";
    }
}
