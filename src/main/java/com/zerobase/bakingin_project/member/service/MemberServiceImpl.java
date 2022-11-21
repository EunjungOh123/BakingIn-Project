package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.member.dto.FindPasswordInput;
import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.exception.MemberErrorCode;
import com.zerobase.bakingin_project.member.exception.MemberException;
import com.zerobase.bakingin_project.member.mail.MailSendService;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import com.zerobase.bakingin_project.member.type.MemberRole;
import com.zerobase.bakingin_project.member.type.MemberStatus;
import com.zerobase.bakingin_project.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MailSendService mailSendService;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void register(RegisterMemberInput registerInput) {

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
    }

    @Override
    public boolean emailAuth(String emailAuthKey) {

        Member member = memberRepository.findByEmailAuthKey(emailAuthKey)
                .orElseThrow(()-> new MemberException(MemberErrorCode.INCORRECT_AUTH_FAIL));

        if (member.isEmailAuthYn()) { // 이미 활성화가 된 상태라면
            return false;
        }

        member.setEmailAuthYn(true)
                .setEmailAuthAt(LocalDateTime.now())
                .setRole(MemberRole.USER)
                .setStatus(MemberStatus.AVAILABLE);

        memberRepository.save(member);

        return true;
    }

    @Override
    @Transactional
    public void findPassword(FindPasswordInput findInput) {
        Member user =
                memberRepository.findByUserIdAndAndEmailAndUserName(findInput.getUserId(), findInput.getEmail(), findInput.getUserName())
                        .orElseThrow(()-> new MemberException(MemberErrorCode.FAIL_TO_FIND_PASSWORD));

        String tmpPassword = getRandomPassword(15);

        String subject = "[본인 인증] 임시 비밀번호 발급을 위한 이메일 인증을 완료해주세요.";
        String text = mailSendService.createFindPasswordTextMessage(findInput.getUserId(), tmpPassword);
        mailSendService.sendMail(findInput.getEmail(), subject, text);

        String encPassword = PasswordUtils.encPassword(tmpPassword);
        user.setPassword(encPassword);
        memberRepository.save(user);
    }
    private String getRandomPassword(int size) {
        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&' };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int idx = 0;
        int len = charSet.length;
        for (int i=0; i<size; i++) {
            idx = sr.nextInt(len);    // 강력한 난수를 발생시키기 위해 SecureRandom 사용
            sb.append(charSet[idx]);
        }

        return sb.toString();
    }
}
