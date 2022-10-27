package com.zerobase.bakingin_project.member.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USERID_ALREADY_USE("이미 사용 중인 아이디입니다."),
    EMAIL_ALREADY_USE("이미 가입된 이메일입니다."),
    PASSWORD_PASSWORDCHECK_UNMATCH("비밀번호를 다시 입력해주세요."),

    USER_STATUS_STOP("현재 정지된 계정입니다."),

    USER_STATUS_WITHDRAW("탈퇴한 회원입니다.");

    private final String value;
}
