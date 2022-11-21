package com.zerobase.bakingin_project.board.like.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HeartErrorCode {

    CANNOT_FIND_POST("글을 찾을 수 없어 좋아요 등록에 실패하였습니다."),
    CANNOT_FIND_USER("사용자를 찾을 수 없어 좋아요 등록에 실패하였습니다."),
    ALREADY_REGISTER_LIKE("이미 좋아요를 눌렀습니다."),
    CANNOT_REGISTER_LIKE_MINE("자신의 레시피에 좋아요를 등록할 수 없습니다.");

    private final String value;
}
