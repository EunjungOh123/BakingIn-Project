package com.zerobase.bakingin_project.member.exception;

import com.zerobase.bakingin_project.member.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberStatusException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public MemberStatusException(ErrorCode errorCode){
        this.errorCode = errorCode;
        this.message = errorCode.getValue();
    }
}
