package com.zerobase.bakingin_project.member.controller;

import com.zerobase.bakingin_project.member.dto.ChangePasswordInput;
import com.zerobase.bakingin_project.member.dto.FindPasswordInput;
import com.zerobase.bakingin_project.member.dto.MemberDto;
import com.zerobase.bakingin_project.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

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
        memberService.findPassword(findInput);
        return "member/find-password-success";
    }

    @GetMapping("/profile/my-info")
    public String myInfo (Principal user, Model model) {
        MemberDto memberDto = memberService.getUserInfo(user.getName());
        model.addAttribute("user", memberDto);
        return "member/profile/my-info";
    }

    @GetMapping("/profile/change-password")
    public String changePassword () {
        return "member/profile/change-password";
    }
    @PostMapping("/profile/change-password")
    public String changeSubmit(ChangePasswordInput passwordInput, Principal user) {
        passwordInput.setUsersId(user.getName());
        memberService.changePassword(passwordInput);
        return "redirect:/member/profile/my-info";
    }
    @GetMapping("/profile/withdraw")
    public String withdraw () {
        return "member/profile/withdraw";
    }
    @PostMapping("/profile/withdraw")
    public String withdraw (Principal user, String password) {
        memberService.withdraw(user.getName(), password);
        return "redirect:/member/logout";
    }
}
