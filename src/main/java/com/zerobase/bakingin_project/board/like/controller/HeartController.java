package com.zerobase.bakingin_project.board.like.controller;

import com.zerobase.bakingin_project.board.like.exception.HeartErrorCode;
import com.zerobase.bakingin_project.board.like.exception.HeartException;
import com.zerobase.bakingin_project.board.like.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/like")
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/post/{id}")
    @PreAuthorize("isAuthenticated()")
    public String registerHeart (@PathVariable (name = "id") Long boardId, Principal user) {
        if(user == null) {
            throw new HeartException(HeartErrorCode.CANNOT_FIND_USER);
        }
        heartService.postHeart(boardId, user.getName());
        return "redirect:/board/detail?id="+boardId;
    }
}
