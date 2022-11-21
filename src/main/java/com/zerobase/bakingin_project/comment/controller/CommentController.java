package com.zerobase.bakingin_project.comment.controller;

import com.zerobase.bakingin_project.comment.dto.CommentDto;
import com.zerobase.bakingin_project.comment.dto.InputComment;
import com.zerobase.bakingin_project.comment.exception.CommentErrorCode;
import com.zerobase.bakingin_project.comment.exception.CommentException;
import com.zerobase.bakingin_project.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/board/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public String addSubmit (InputComment input, Principal user) {
        if(user == null) {
            throw new CommentException(CommentErrorCode.CANNOT_WRITE_COMMENT);
        }
        commentService.add(input,user.getName());

        return "redirect:/board/detail?id="+input.getBoardId();
    }

    @GetMapping("/update/{id}")
    public String update (Model model, @PathVariable Long id, Principal user){
        if(user == null) {
            throw new CommentException(CommentErrorCode.CANNOT_REVISE_COMMENT);
        }
        CommentDto comment = commentService.detailComment(id);
        if (!user.getName().equals(comment.getWriter().getUserId())) {
            throw new CommentException(CommentErrorCode.CANNOT_REVISE_COMMENT);
        }

        model.addAttribute("comment", comment);

        return "board/comment/update";
    }

    @PostMapping("/update")
    public String updateSubmit (InputComment input, Long commentId) {
        commentService.update(input, commentId);
        return "redirect:/board/detail?id="+input.getBoardId();
    }

    @PostMapping("/delete")
    public String delete (Long commentId, Long boardId) {
        commentService.delete(commentId);
        return "redirect:/board/detail?id="+boardId;
    }
}
