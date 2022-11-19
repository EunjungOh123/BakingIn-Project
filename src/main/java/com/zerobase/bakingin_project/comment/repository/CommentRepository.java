package com.zerobase.bakingin_project.comment.repository;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardOrderByIdAsc(Board board);

    int countByBoard(Board board);
}