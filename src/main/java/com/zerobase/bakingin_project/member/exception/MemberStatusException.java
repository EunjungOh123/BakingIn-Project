package com.zerobase.bakingin_project.member.exception;

import com.zerobase.bakingin_project.member.type.MemberErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberStatusException extends RuntimeException{
    private MemberErrorCode memberErrorCode;
    private String message;

    public MemberStatusException(MemberErrorCode memberErrorCode){
        this.memberErrorCode = memberErrorCode;
        this.message = memberErrorCode.getValue();
    }
}
