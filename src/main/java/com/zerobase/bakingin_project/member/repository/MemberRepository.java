package com.zerobase.bakingin_project.member.repository;

import com.zerobase.bakingin_project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByEmail(String email);
    Optional<Member> findByEmailAuthKey (String emailAuthKey);
    Optional<Member> findByUserIdAndAndEmailAndUserName(String userId, String email, String userName);
    List<Member> findAllByWithdrawUserIsTrue();
}
