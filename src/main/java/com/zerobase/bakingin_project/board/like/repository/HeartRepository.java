package com.zerobase.bakingin_project.board.like.repository;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.board.like.entity.Heart;
import com.zerobase.bakingin_project.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeartRepository extends JpaRepository<Heart, Long> {
    Optional<Heart> findByBoardAndUser(Board board, Member user);
    int countByBoard(Board board);
}
