package com.zerobase.bakingin_project.member.service;

import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.exception.MemberStatusException;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import com.zerobase.bakingin_project.member.exception.MemberErrorCode;
import com.zerobase.bakingin_project.member.type.MemberRole;
import com.zerobase.bakingin_project.member.type.MemberStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
 UserDetailsService는 Spring Security에서 유저의 정보를 가져오는 인터페이스
 기본 오버라이딩 메서드는 loadUserByUsername
 유저의 정보를 불러와서 UserDetails로 리턴
*/

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        /*
         userId로 SiteUser 객체를 조회
         만약 userId에 해당하는 데이터가 없을 경우에는 UsernameNotFoundException 오류
        */
        if(!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("사용자가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if(MemberStatus.STOP == (member.getStatus())) {
            throw new MemberStatusException(MemberErrorCode.USER_STATUS_STOP);
        }

        if(MemberStatus.WITHDRAW == member.getStatus()) {
            throw new MemberStatusException(MemberErrorCode.USER_STATUS_WITHDRAW);
        }
        /* RoleType에 따라 권한을 부여*/
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();

        if(member.getRole() == MemberRole.GUEST) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(MemberRole.GUEST.getValue()));
        } else if (member.getRole() == MemberRole.ADMIN) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
        } else {
            grantedAuthorityList.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
        }
        // 스프링 시큐리티의 User 객체를 생성하여 리턴
        return new User(member.getUserId(), member.getPassword(), grantedAuthorityList);
    }
}
