package com.zerobase.bakingin_project.member.controller;

import com.zerobase.bakingin_project.member.dto.FindPasswordInput;
import com.zerobase.bakingin_project.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Service
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberSettingsController {

    private final MemberService memberService;

    /**
     * 임시 비밀번호 발급을 위한 본인 계정 확인 절차 페이지
     */
    @GetMapping("/find-password")
    public String findPassword() {
        return "member/find-password";
    }
    @PostMapping("/find-password")
    public String findPasswordSubmit(FindPasswordInput findInput) {
        System.out.println(findInput.getUserId()+" "+findInput.getUserName()+" "+findInput.getEmail());
        memberService.findPassword(findInput);
        return "member/find-password-success";
    }

}
