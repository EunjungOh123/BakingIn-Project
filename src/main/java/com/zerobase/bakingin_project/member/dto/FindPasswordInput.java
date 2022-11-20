package com.zerobase.bakingin_project.member.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindPasswordInput {

    private String userId;

    private String userName;

    private String email;
}
