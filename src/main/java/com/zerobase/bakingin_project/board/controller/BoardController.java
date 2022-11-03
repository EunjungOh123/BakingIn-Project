package com.zerobase.bakingin_project.board.controller;

import com.zerobase.bakingin_project.admin.category.service.CategoryService;
import com.zerobase.bakingin_project.board.dto.BoardDto;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.board.exception.BoardException;
import com.zerobase.bakingin_project.board.service.BoardService;
import com.zerobase.bakingin_project.board.type.BoardErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final CategoryService categoryService;
    private final BoardService boardService;
    @GetMapping("/add")
    public String add (Model model, Principal user) {
        if(user == null) {
            throw new BoardException(BoardErrorCode.CANNOT_WRITE_POST);
        }
        String userId = user.getName();
        model.addAttribute("userId", userId);
        model.addAttribute("category", categoryService.frontList());
        return "board/add";
    }
    @PostMapping("/add")
    public String addSubmit (InputBoard inputBoard, Authentication authentication) {
        String userId = authentication.getName();
        boardService.add(inputBoard, userId);
        return "redirect:/board/list";
    }

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/list";
    }
    @GetMapping("/detail")
    public String detail (@RequestParam long id, Model model) {
        BoardDto boardDto = boardService.recipeDetail(id);
        model.addAttribute("boardDto", boardDto);
        return "board/detail";
    }
}
