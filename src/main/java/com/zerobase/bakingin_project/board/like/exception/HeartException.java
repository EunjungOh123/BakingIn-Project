package com.zerobase.bakingin_project.board.like.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeartException extends RuntimeException{

    private HeartErrorCode heartErrorCode;
    private String message;

    public HeartException(HeartErrorCode heartErrorCode) {
        this.heartErrorCode = heartErrorCode;
        this.message = heartErrorCode.getValue();
    }
}
