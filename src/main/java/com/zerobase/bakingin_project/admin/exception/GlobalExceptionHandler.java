package com.zerobase.bakingin_project.admin.exception;

/* 서버에서 발생하는 오류가 발생했을 때 이를 필터링해주고 오류 페이지로 처리 */

import com.zerobase.bakingin_project.board.exception.BoardException;
import com.zerobase.bakingin_project.board.like.exception.HeartException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({BoardException.class, CategoryException.class, HeartException.class})
    public String handleException (Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("error", e.getMessage());
        return "error/error";
    }
}
