package com.zerobase.bakingin_project.board.repository;

import com.zerobase.bakingin_project.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board, Long> {
}
