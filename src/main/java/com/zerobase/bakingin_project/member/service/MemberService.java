package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.member.dto.FindPasswordInput;
import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;


public interface MemberService {

    /**
     * 회원 가입
     */
    void register (RegisterMemberInput registerInput);


    /**
     * 이메일 인증
     */
    boolean emailAuth (String emailAuthKey);

    /**
     * 비밀번호 분실 시 임시 비밀번호 발급
     */
    void findPassword(FindPasswordInput findInput);
}
