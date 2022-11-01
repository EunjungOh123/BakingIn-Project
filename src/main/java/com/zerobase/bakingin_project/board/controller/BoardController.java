package com.zerobase.bakingin_project.board.controller;

import com.zerobase.bakingin_project.admin.category.service.CategoryService;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import com.zerobase.bakingin_project.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final CategoryService categoryService;
    private final BoardService boardService;

    @GetMapping("/add")
    public String add (Model model, Principal user) {

        String userId = user.getName();

        model.addAttribute("userId", userId);
        model.addAttribute("category", categoryService.list());

        return "/board/add";
    }

    @PostMapping("/add")
    public String addSubmit (InputBoard inputBoard, Authentication authentication) {
        String userId = authentication.getName();
        boardService.add(inputBoard, userId);
        return "/board/add";
    }

}
