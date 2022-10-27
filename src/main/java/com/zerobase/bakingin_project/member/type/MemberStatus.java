package com.zerobase.bakingin_project.member.type;


public enum MemberStatus {


    /**
     * 현재 가입 요청중
     */

    REQUEST,


    /**
     * 현재 이용중인 상태
     */

    AVAILABLE,

    /**
     * 현재 정지된 상태
     */

    STOP,

    /**
     * 현재 탈퇴된 회원
     */

    WITHDRAW
}
