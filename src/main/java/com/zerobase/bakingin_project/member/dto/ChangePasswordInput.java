package com.zerobase.bakingin_project.member.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangePasswordInput {
    private String usersId;
    private String password;
    private String newPassword;
    private String newRePassword;
}
