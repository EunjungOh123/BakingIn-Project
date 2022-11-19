package com.zerobase.bakingin_project.comment.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentException extends RuntimeException {
    private CommentErrorCode commentErrorCode;
    private String message;

    public CommentException(CommentErrorCode commentErrorCode) {
        this.commentErrorCode = commentErrorCode;
        this.message = commentErrorCode.getValue();
    }
}
