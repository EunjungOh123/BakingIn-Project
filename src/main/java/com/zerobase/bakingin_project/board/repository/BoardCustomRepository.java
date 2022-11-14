package com.zerobase.bakingin_project.board.repository;

import com.zerobase.bakingin_project.board.entity.Board;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class BoardCustomRepository {

    @PersistenceContext
    private EntityManager em;

    public int findAllCnt() {
        return ((Number) em.createQuery("select count (b) from Board b")
                .getSingleResult()).intValue();
    }
    public List<Board> findListPaging(int startIndex, int pageSize) {
        List<Board> boards = em.createQuery("select b from Board b order by b.id desc ", Board.class)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .getResultList();
        return boards;
    }
}
