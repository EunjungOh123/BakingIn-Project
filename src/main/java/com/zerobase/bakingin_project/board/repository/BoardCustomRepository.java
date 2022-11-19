package com.zerobase.bakingin_project.board.repository;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.board.model.BoardParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepository {
    @PersistenceContext
    private EntityManager em;


    public int searchAllCnt(BoardParam param) {
        Query query;
        if (param.getCategoryId() != null) {
            query = searchCountQuery(param)
                    .setParameter("categoryId", param.getCategoryId());
            return ((Number) query.getSingleResult()).intValue();
        }
        query = searchCountQuery(param);
        return ((Number) query.getSingleResult()).intValue();
    }

    public int boardListCnt(BoardParam param) {
        Query query;
        if (param.getCategoryId() != null) {
            query = em.createQuery("select count (b) from Board b where b.deleteBoard = false "
                            + " and b.category.id = :categoryId")
                    .setParameter("categoryId", param.getCategoryId());
            return ((Number) query.getSingleResult()).intValue();
        }
        query = em.createQuery("select count (b) from Board b where b.deleteBoard = false");
        return ((Number) query.getSingleResult()).intValue();
    }

    public List<Board> searchListPaging(int startIndex, int pageSize, BoardParam param) {
        Query query;
        if (param.getCategoryId() != null) {
            query = searchListQuery(param)
                    .setParameter("categoryId", param.getCategoryId());
            System.out.println(query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList().size());
            return query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
        }
        query = searchListQuery(param);
        System.out.println(query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList().size());
        return query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
    }

    public List<Board> boardListPaging(int startIndex, int pageSize, BoardParam param) {
        Query query;
        if (param.getCategoryId() != null) {
            query = em.createQuery("select b from Board b " +
                            "where b.category.id = : categoryId and b.deleteBoard = false " +
                            "order by b.id desc", Board.class)
                    .setParameter("categoryId", param.getCategoryId());
            return query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
        }
        query = em.createQuery("select b from Board b where b.deleteBoard = false " +
                "order by b.id desc", Board.class);
        return query.setFirstResult(startIndex).setMaxResults(pageSize).getResultList();
    }

    private TypedQuery<Board> searchListQuery(BoardParam param) {
        String whereQuery1 = "where b.deleteBoard = false ";
        String whereQuery2 = "and b.category.id =:categoryId ";
        String orderQuery = "order by b.id desc";
        if (param.getCategoryId() != null) {
            whereQuery1 += whereQuery2;
        }
        switch (param.getSearchType()) {
            case "title":
                return em.createQuery("select b from Board b " + whereQuery1 +
                                "and b.title like : searchValue " + orderQuery, Board.class)
                        .setParameter("searchValue", "%" + param.getSearchValue() + "%");
            case "writer":
                return em.createQuery("select b from Board b " + whereQuery1 +
                                "and b.writer.userId like : searchValue " + orderQuery, Board.class)
                        .setParameter("searchValue", "%" + param.getSearchValue() + "%");
            case "contents":
                return em.createQuery("select b from Board b " + whereQuery1 +
                                "and b.contents like : searchValue " + orderQuery, Board.class)
                        .setParameter("searchValue", "%" + param.getSearchValue() + "%");
        }
        return em.createQuery("select b from Board b " +
                whereQuery1 + orderQuery, Board.class);
    }

    private Query searchCountQuery(BoardParam param) {
        String whereQuery1 = "where b.deleteBoard = false ";
        String whereQuery2 = "and b.category.id= :categoryId ";
        String orderQuery = "order by b.id desc";
        if (param.getCategoryId() != null) {
            whereQuery1 += whereQuery2;
        }
        switch (param.getSearchType()) {
            case "title":
                return em.createQuery("select count(b) from Board b " + whereQuery1 +
                                "and b.title like : searchValue " + orderQuery)
                        .setParameter("searchValue", "%" + param.getSearchValue() + "%");
            case "writer":
                return em.createQuery("select count(b) from Board b " + whereQuery1 +
                                "and b.writer.userId like : searchValue " + orderQuery)
                        .setParameter("searchValue", "%" + param.getSearchValue() + "%");
            case "contents":
                return em.createQuery("select count(b) from Board b " + whereQuery1 +
                                "and b.contents like : searchValue " + orderQuery)
                        .setParameter("searchValue", "%" + param.getSearchValue() + "%");
        }
        return em.createQuery("select count(b) from Board b " +
                whereQuery1 + orderQuery);
    }
}
