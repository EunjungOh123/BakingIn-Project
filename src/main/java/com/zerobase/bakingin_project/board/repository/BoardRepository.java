package com.zerobase.bakingin_project.board.repository;

import com.zerobase.bakingin_project.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BoardRepository extends JpaRepository<Board, Long> {
    @Modifying
    @Query("update Board b set b.views = b.views + 1 where b.id = :id")
    int updateView(@Param("id") Long id);
    /**
     * deleteBoard == true 해당되는 모든 게시물 리턴
     */
    List<Board> findAllByDeleteBoardIsTrue();
}
