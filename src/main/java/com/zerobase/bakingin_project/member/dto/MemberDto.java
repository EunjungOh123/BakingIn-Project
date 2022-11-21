package com.zerobase.bakingin_project.member.dto;

import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.type.MemberRole;
import com.zerobase.bakingin_project.member.type.MemberStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class MemberDto {
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phone;
    private boolean emailAuthYn;
    private MemberRole role;
    private LocalDateTime createAt;
    private MemberStatus memberStatus;

    public static MemberDto fromEntity (Member user) {
        return MemberDto.builder()
                .userId(user.getUserId())
                .userName(user.getUserName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .emailAuthYn(user.isEmailAuthYn())
                .role(user.getRole())
                .createAt(user.getCreateAt())
                .memberStatus(user.getStatus())
                .build();
    }
}
