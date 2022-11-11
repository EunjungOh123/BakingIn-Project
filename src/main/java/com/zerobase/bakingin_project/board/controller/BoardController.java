package com.zerobase.bakingin_project.board.controller;

import com.zerobase.bakingin_project.admin.category.service.CategoryService;
import com.zerobase.bakingin_project.board.dto.BoardDto;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import com.zerobase.bakingin_project.board.exception.BoardException;
import com.zerobase.bakingin_project.board.repository.BoardCustomRepository;
import com.zerobase.bakingin_project.board.service.BoardService;
import com.zerobase.bakingin_project.board.exception.BoardErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {
    private final CategoryService categoryService;
    private final BoardService boardService;
    private final BoardCustomRepository boardRepository;
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
    public String list(Model model, @RequestParam(defaultValue = "1") int page) {
        // 총 게시물 수
        int totalListCnt = boardRepository.findAllCnt();
        Pagination pagination = new Pagination(totalListCnt, page);
        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        List<BoardDto> list = boardRepository.findListPaging(startIndex, pageSize)
                .stream().map(e -> BoardDto.fromEntity(e)).collect(Collectors.toList());

        model.addAttribute("list", list);
        model.addAttribute("pagination", pagination);

        return "board/list";
    }
    @GetMapping("/detail")
    public String detail (@RequestParam Long id, Model model, HttpServletRequest request,  HttpServletResponse response) {

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
                oldCookie.setMaxAge(60 * 60 * 24); 							// 쿠키 시간
                response.addCookie(oldCookie);
            }
        } else {
            boardService.updateViews(id);
            Cookie newCookie = new Cookie("postView", "[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24); 								// 쿠키 시간
            response.addCookie(newCookie);
        }

        BoardDto boardDto = boardService.recipeDetail(id);

        model.addAttribute("boardDto", boardDto);
        return "board/detail";
    }
}
