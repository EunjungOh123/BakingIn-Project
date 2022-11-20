package com.zerobase.bakingin_project.member.validator.RegisterValidatorInher;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import com.zerobase.bakingin_project.member.exception.MemberErrorCode;
import com.zerobase.bakingin_project.member.validator.RegisterValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/* 아이디 중복 오류 */
@RequiredArgsConstructor
@Component
public class CheckUserIdValidator extends RegisterValidator<RegisterMemberInput> {

    private final MemberRepository memberRepository;
    @Override
    protected void doValidate(RegisterMemberInput dto, Errors errors) {
        if(memberRepository.existsById(dto.getUserId())) {
            errors.rejectValue("userId", "아이디 중복 오류",
                    MemberErrorCode.USERID_ALREADY_USE.getValue());
        }
    }
}
