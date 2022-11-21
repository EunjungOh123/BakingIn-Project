package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.board.model.Pagination;
import com.zerobase.bakingin_project.member.dto.ChangePasswordInput;
import com.zerobase.bakingin_project.member.dto.FindPasswordInput;
import com.zerobase.bakingin_project.member.dto.MemberDto;
import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;

import java.util.List;


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

    /**
     * 회원 턀퇴
     */
    void withdraw(String userId, String password);
    /**
     * 회원 정보
     */
    MemberDto getUserInfo (String userId);
    /**
     * 회원 정보에서 비밀번호 변경
     */
    void changePassword (ChangePasswordInput changePasswordInput);
    /**
     * 회원 목록  (관리자에서 사용)
     */
    List<MemberDto> userList(Pagination pagination);
    /**
     * 회원 수  (관리자에서 사용)
     */
    int totalMember ();
    /**
     * 회원 상태 수정 (관리자에서 사용)
     */
    void updateUserStatus (String userId, String memberStatus);
}
