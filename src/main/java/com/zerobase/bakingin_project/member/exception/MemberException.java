package com.zerobase.bakingin_project.member.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberException extends RuntimeException{
    private MemberErrorCode memberErrorCode;
    private String message;

    public MemberException(MemberErrorCode memberErrorCode){
        this.memberErrorCode = memberErrorCode;
        this.message = memberErrorCode.getValue();
    }
}
