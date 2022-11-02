package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.mail.MailSendService;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import com.zerobase.bakingin_project.member.type.MemberRole;
import com.zerobase.bakingin_project.member.type.MemberStatus;
import com.zerobase.bakingin_project.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MailSendService mailSendService;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public boolean register(RegisterMemberInput registerInput) {

        String encPassword = PasswordUtils.encPassword(registerInput.getPassword());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(registerInput.getUserId())
                .password(encPassword)
                .userName(registerInput.getUserName())
                .phone(registerInput.getPhone())
                .email(registerInput.getEmail())
                .emailAuthKey(uuid)
                .emailAuthYn(false)
                .status(MemberStatus.REQUEST)
                .role(MemberRole.GUEST)
                .build();

        memberRepository.save(member);

        String subject = "[회원 가입] 이메일 인증을 완료해주세요.";
        String text = mailSendService.createRegisterTextMessage(registerInput.getUserId(), uuid);

        mailSendService.sendMail(registerInput.getEmail(), subject, text);

        return true;
    }

    @Override
    public boolean emailAuth(String emailAuthKey) {
        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(emailAuthKey);
        if (!optionalMember.isPresent()) {
            return false;
        }
        Member member = optionalMember.get();

        if(member.isEmailAuthYn()) { // 이미 활성화가 된 상태라면
            return false;
        }

        member.setEmailAuthYn(true)
                .setEmailAuthAt(LocalDateTime.now())
                .setRole(MemberRole.USER)
                .setStatus(MemberStatus.AVAILABLE);

        memberRepository.save(member);

        return true;
    }
}
