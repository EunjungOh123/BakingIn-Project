package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import com.zerobase.bakingin_project.member.type.MemberRole;
import com.zerobase.bakingin_project.member.type.MemberStatus;
import com.zerobase.bakingin_project.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public boolean register(RegisterMemberInput registerInput) {

        String encPassword = PasswordUtils.encPassword(registerInput.getPassword());

        Member member = Member.builder()
                .userId(registerInput.getUserId())
                .password(encPassword)
                .userName(registerInput.getUserName())
                .phone(registerInput.getPhone())
                .email(registerInput.getEmail())
                .emailAuthYn(false)
                .status(MemberStatus.REQUEST)
                .role(MemberRole.GUEST)
                .build();

        memberRepository.save(member);

        return true;
    }

    @Override
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> validatorResult = new HashMap<>();

        for (FieldError error : errors.getFieldErrors()) {
            String validKeyName = "valid_" + error.getField();
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
