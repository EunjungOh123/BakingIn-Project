package com.zerobase.bakingin_project.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminMainController {

    @GetMapping("/admin/main") // 관리자 메인 페이지
    public String main() {
        return "admin/main";
    }

}
