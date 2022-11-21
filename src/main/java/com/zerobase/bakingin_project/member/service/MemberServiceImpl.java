package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.board.model.Pagination;
import com.zerobase.bakingin_project.member.dto.ChangePasswordInput;
import com.zerobase.bakingin_project.member.dto.FindPasswordInput;
import com.zerobase.bakingin_project.member.dto.MemberDto;
import com.zerobase.bakingin_project.member.dto.RegisterMemberInput;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.exception.MemberErrorCode;
import com.zerobase.bakingin_project.member.exception.MemberException;
import com.zerobase.bakingin_project.member.mail.MailSendService;
import com.zerobase.bakingin_project.member.repository.MemberCustomRepository;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import com.zerobase.bakingin_project.member.type.MemberRole;
import com.zerobase.bakingin_project.member.type.MemberStatus;
import com.zerobase.bakingin_project.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService {

    private final MailSendService mailSendService;

    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;

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
                .withdrawUser(false)
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

        String subject = "[본인 인증 완료] 임시 비밀번호 발급";
        String text = mailSendService.createFindPasswordTextMessage(findInput.getUserId(), tmpPassword);
        mailSendService.sendMail(findInput.getEmail(), subject, text);

        String encPassword = PasswordUtils.encPassword(tmpPassword);
        user.setPassword(encPassword);
        memberRepository.save(user);
    }

    @Override
    public void withdraw(String userId, String password) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(()-> new MemberException(MemberErrorCode.FAIL_TO_WITHDRAW_NOT_EXIST_USER));

        if (!PasswordUtils.equals(password, user.getPassword())) {
            throw new MemberException(MemberErrorCode.FAIL_TO_WITHDRAW_INCORRECT_PASSWORD);
        }
        user.setWithdrawUser(true)
                .setUserName("탈퇴회원")
                .setPhone("")
                .setEmail("")
                .setPassword("")
                .setEmailAuthKey("")
                .setEmailAuthYn(false)
                .setEmailAuthAt(null)
                .setStatus(MemberStatus.WITHDRAW);
        memberRepository.save(user);
    }

    @Override
    public MemberDto getUserInfo(String userId) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(()-> new MemberException(MemberErrorCode.CANNOT_INQUIRE_USERINFO));
        return MemberDto.fromEntity(user);
    }

    @Override
    public void changePassword(ChangePasswordInput changeInput) {
        Member user = memberRepository.findById(changeInput.getUsersId())
                .orElseThrow(()-> new MemberException(MemberErrorCode.CANNOT_CHANGE_PASSWORD_NOT_EXIST_USER));

        if(PasswordUtils.equals(changeInput.getPassword(), user.getPassword())) {
            throw new MemberException(MemberErrorCode.USER_PASSWORD_NOT_CORRECT);
        }

        user.setPassword(PasswordUtils.encPassword(changeInput.getNewPassword()));

        memberRepository.save(user);
    }

    @Override
    public List<MemberDto> userList(Pagination pagination) {
        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        return memberCustomRepository.findListPaging(startIndex, pageSize)
                .stream().map((e)-> MemberDto.fromEntity(e)).collect(Collectors.toList());
    }

    @Override
    public int totalMember() {
        return memberCustomRepository.findAllCnt();
    }

    @Override
    public void updateUserStatus(String userId, String memberStatus) {
        Member user = memberRepository.findById(userId)
                .orElseThrow(()-> new MemberException(MemberErrorCode.FAIL_TO_UPDATE_STATUS_NOT_EXIST_USER));
        user.setStatus(MemberStatus.valueOf(memberStatus));
        memberRepository.save(user);
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void deleteUserInDB () {
        List<Member> member = memberRepository.findAllByWithdrawUserIsTrue();
        if(!member.isEmpty()) {
            memberRepository.deleteAll(member);
            log.info("데이터베이스에서 탈퇴한 유저 일괄 삭제!");
        }
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
