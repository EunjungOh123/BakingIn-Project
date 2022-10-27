package com.zerobase.bakingin_project.member.dto;

/* 회원가입의 Form 데이터 전달에 활용할 객체 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterMemberInput {

    @Size(min = 5, max = 15, message = "아이디를 5~15자 사이로 입력해주세요.")
    private String userId;

    @Pattern(regexp="(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,15}", // 공백 허용 X
            message = "영어 소문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~15자여야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 입력 값입니다.")
    private String passwordCheck;

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(min = 2, max = 8, message = "이름을 2~8자 사이로 입력해주세요.")
    private String userName;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "- 없이 휴대폰 번호를 입력해주세요.")
    private String phone;
}
