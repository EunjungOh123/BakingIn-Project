package com.zerobase.bakingin_project.member.repository;

import com.zerobase.bakingin_project.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public int findAllCnt() {
        return ((Number) em.createQuery("select count (m) from Member m")
                .getSingleResult()).intValue();
    }

    public List<Member> findListPaging(int startIndex, int pageSize) {
        return em.createQuery("select m from Member m", Member.class)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();

    }
}
