package com.zerobase.bakingin_project.board.controller;

import com.zerobase.bakingin_project.admin.category.service.CategoryService;
import com.zerobase.bakingin_project.board.dto.BoardDto;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import com.zerobase.bakingin_project.board.exception.BoardException;
import com.zerobase.bakingin_project.board.like.service.HeartService;
import com.zerobase.bakingin_project.board.model.BoardParam;
import com.zerobase.bakingin_project.board.model.Pagination;
import com.zerobase.bakingin_project.board.service.BoardService;
import com.zerobase.bakingin_project.board.exception.BoardErrorCode;
import com.zerobase.bakingin_project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final CommentService commentService;
    private final HeartService heartService;
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
    public String list(Model model, BoardParam boardParam,
                       @RequestParam(defaultValue = "1") int page
    ) {
        Pagination pagination =
                new Pagination(boardService.totalRecipeBoard(boardParam), page);
        String query = boardParam.getBoardsLink();
        model.addAttribute("list", boardService.list(pagination, boardParam));
        model.addAttribute("category", categoryService.frontList());
        model.addAttribute("pagination", pagination);
        model.addAttribute("query", query);
        return "board/list";
    }
    @GetMapping("/detail")
    public String detail (@RequestParam Long id, Model model,
                          HttpServletRequest request,  HttpServletResponse response) {

        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("postView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("["+ id +"]")) {
                boardService.updateViews(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60); 							// 쿠키 시간
                response.addCookie(oldCookie);
            }
        } else {
            boardService.updateViews(id);
            Cookie newCookie = new Cookie("postView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60); 								// 쿠키 시간
            response.addCookie(newCookie);
        }
        BoardDto boardDto = boardService.recipeDetail(id);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("commentList", commentService.list(boardDto.getId()));
        model.addAttribute("commentsCount", commentService.listCount(boardDto.getId()));
        model.addAttribute("boardLikeCnt", heartService.countHeartByBoard(id));
        return "board/detail";
    }
    @PostMapping("/delete")
    public String delete (@RequestParam Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }
    @GetMapping("/update")
    public String update (Model model, @RequestParam Long id, Principal user) {
        if(user == null) {
            throw new BoardException(BoardErrorCode.CANNOT_REVISE_POST);
        }

        BoardDto boardDto = boardService.recipeDetail(id);

        if(!user.getName().equals(boardDto.getWriter().getUserId())) {
            throw new BoardException(BoardErrorCode.UN_MATCH_WRITER_AND_USERID);
        }

        model.addAttribute("category", categoryService.frontList());
        model.addAttribute("boardDto", boardDto);
        return "board/update";
    }
    @PostMapping("/update")
    public String updateSubmit(InputBoard inputBoard, Long id) {
        boardService.update(inputBoard, id);
        return "redirect:/board/detail?id="+id;
    }
}
