package com.zerobase.bakingin_project.board.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BoardErrorCode {

    CANNOT_WRITE_POST ("글을 작성할 수 있는 권한이 없습니다."),
    CHOOSE_INCORRECT_CATEGORY("존재하지 않는 카테고리입니다."),
    CANNOT_FIND_POST("글을 찾을 수 없습니다."),
    CANNOT_REVISE_POST("글을 수정할 수 있는 권한이 없습니다.");


    private final String value;
}
