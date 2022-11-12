package com.zerobase.bakingin_project.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardErrorCode {

    CANNOT_WRITE_POST ("글을 작성할 수 없습니다."),
    CHOOSE_INCORRECT_CATEGORY("존재하지 않는 카테고리입니다."),
    CANNOT_FIND_POST("글을 찾을 수 없습니다."),
    CANNOT_REVISE_POST("글을 수정할 수 없습니다."),
    CANNOT_DELETE_POST("글을 삭제할 수 없습니다."),
    UN_MATCH_WRITER_AND_USERID("작성자와 사용자가 일치하지 않습니다.");


    private final String value;
}
