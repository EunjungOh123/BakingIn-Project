package com.zerobase.bakingin_project.member.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * 회원 가입 시 유효성 검사에 대한 에러 처리
 */
public class ValidateHandler {
        public static Map<String, String> validateHandling(Errors errors) {

        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = "valid_" + error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
