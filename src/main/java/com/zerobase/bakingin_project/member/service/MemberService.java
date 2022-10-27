package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import org.springframework.validation.Errors;

import java.util.Map;

public interface MemberService {

    /**
     * 회원 가입
     */
    boolean register (RegisterMemberInput registerInput);

    /**
     * 회원 가입 시 유효성 검사에 대한 에러 처리
     */
    Map<String, String> validateHandling(Errors errors);
}
