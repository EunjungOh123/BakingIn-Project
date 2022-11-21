package com.zerobase.bakingin_project.admin.controller;

import com.zerobase.bakingin_project.board.model.Pagination;
import com.zerobase.bakingin_project.member.dto.MemberDto;
import com.zerobase.bakingin_project.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {

    private final MemberService memberService;

    @GetMapping("/list")
    public String memberList (Model model, @RequestParam(defaultValue = "1") int page) {
        int memberCnt = memberService.totalMember();
        Pagination pagination = new Pagination(memberCnt, page);

        model.addAttribute("list", memberService.userList(pagination));
        model.addAttribute("pagination", pagination);
        model.addAttribute("total", memberCnt);
        return "/admin/member/list";
    }
    @GetMapping("detail")
    public String memberDetail (Model model, @RequestParam ("id") String userId) {
        MemberDto memberDto = memberService.getUserInfo(userId);
        model.addAttribute("user", memberDto);
        return "admin/member/detail";
    }

    @PostMapping("/status")
    public String status(String userId, String memberStatus) {
        memberService.updateUserStatus(userId, memberStatus);
        return "redirect:/admin/member/detail?id=" + userId;
    }
}
