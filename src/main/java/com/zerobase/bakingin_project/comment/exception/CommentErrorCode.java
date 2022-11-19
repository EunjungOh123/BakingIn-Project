package com.zerobase.bakingin_project.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentErrorCode {

    CANNOT_WRITE_COMMENT ("댓글을 작성할 수 없습니다.", 400),
    CANNOT_FIND_POST("존재하지 않는 게시글입니다.", 400),
    CANNOT_REVISE_COMMENT("댓글을 수정할 수 없습니다.", 400),
    NOT_EXIST_COMMENT("존재하지 않는 댓글입니다.", 400),
    CANNOT_DELETE_COMMENT("댓글을 삭제할 수 없습니다.", 400);

    private final String value;
    private final int statusCode;
}
