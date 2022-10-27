package com.zerobase.bakingin_project.member.validator.RegisterValidatorInher;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.type.ErrorCode;
import com.zerobase.bakingin_project.member.validator.RegisterValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/* 비밀번호 확인 오류 */

@Component
public class CheckPasswordMatchValidator extends RegisterValidator<RegisterMemberInput> {

    @Override
    protected void doValidate(RegisterMemberInput dto, Errors errors) {
        if(!dto.getPassword().equals(dto.getPasswordCheck())) {
            errors.rejectValue("passwordCheck", "비밀번호 확인 오류",
                    ErrorCode.PASSWORD_PASSWORDCHECK_UNMATCH.getValue());
        }
    }
}
