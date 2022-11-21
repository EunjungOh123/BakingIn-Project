package com.zerobase.bakingin_project.board.like.entity;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.member.entity.Member;
import lombok.*;

import javax.persistence.*;
@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Heart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private Member user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;
}
