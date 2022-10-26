package com.zerobase.bakingin_project.member.repository;

import com.zerobase.bakingin_project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
    boolean existsByEmail(String email);
}
