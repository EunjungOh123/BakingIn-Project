package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;


public interface MemberService {

    /**
     * 회원 가입
     */
    boolean register (RegisterMemberInput registerInput);


    /**
     * 이메일 인증
     */
    boolean emailAuth (String emailAuthKey);
}
