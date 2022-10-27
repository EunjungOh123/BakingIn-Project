package com.zerobase.bakingin_project.member.validator.RegisterValidatorInher;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import com.zerobase.bakingin_project.member.type.MemberErrorCode;
import com.zerobase.bakingin_project.member.validator.RegisterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/* 이메일 중복 오류 */
@RequiredArgsConstructor
@Component
public class CheckEmailValidator extends RegisterValidator<RegisterMemberInput> {

    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(RegisterMemberInput dto, Errors errors) {
        if(memberRepository.existsByEmail(dto.getEmail())) {
            errors.rejectValue("email", "이메일 중복 오류",
                    MemberErrorCode.EMAIL_ALREADY_USE.getValue());
        }
    }
}
