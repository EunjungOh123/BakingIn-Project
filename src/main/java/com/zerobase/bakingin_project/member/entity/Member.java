package com.zerobase.bakingin_project.member.entity;

import com.zerobase.bakingin_project.entity.BaseTimeEntity;
import com.zerobase.bakingin_project.member.type.MemberRole;
import com.zerobase.bakingin_project.member.type.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true) //  Chain 형태로 이어서 원하는 set 메서드를 생성
public class Member extends BaseTimeEntity {
    @Id
    private String userId;
    private String password;
    private String userName;
    private String email;
    private String phone;

    private boolean emailAuthYn;
    private LocalDateTime emailAuthAt;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;
}