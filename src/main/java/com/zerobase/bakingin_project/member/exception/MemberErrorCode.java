package com.zerobase.bakingin_project.member.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberErrorCode {
    // 회원 가입 시
    USERID_ALREADY_USE("이미 사용 중인 아이디입니다."),
    EMAIL_ALREADY_USE("이미 가입된 이메일입니다."),
    PASSWORD_PASSWORDCHECK_UNMATCH("비밀번호를 다시 확인해주세요."),
    // 로그인 시
    USER_NOT_EXIST_FAIL_LOGIN("존재하지 않는 계정입니다. 다시 입력해주세요."),
    USER_PASSWORD_NOT_CORRECT("비밀번호가 맞지 않습니다. 다시 입력해주세요."),
    USER_STATUS_STOP("현재 정지된 계정입니다."),
    USER_STATUS_WITHDRAW("탈퇴한 회원입니다."),

    // 기타 오류 코드
    INCORRECT_AUTH_FAIL("유효하지 않은 이메일 인증키입니다."),
    FAIL_TO_FIND_PASSWORD("본인 인증에 실패하였습니다. 다시 입력해주세요."),
    FAIL_TO_WITHDRAW_INCORRECT_PASSWORD("비밀번호 입력이 잘못되어 회원 탈퇴에 실패하였습니다."),
    FAIL_TO_WITHDRAW_NOT_EXIST_USER("계정이 존재하지 않아 회원 탈퇴에 실패하였습니다."),
    CANNOT_INQUIRE_USERINFO("사용자 정보를 조회할 수 없습니다."),
    CANNOT_CHANGE_PASSWORD_NOT_EXIST_USER("계정이 존재하지 않아 비밀번호 변경에 실패하였습니다."),
    FAIL_TO_UPDATE_STATUS_NOT_EXIST_USER("계정이 존재하지 않아 사용자 상태를 변경할 수 없습니다.");
    private final String value;
}
