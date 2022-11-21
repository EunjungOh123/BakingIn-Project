package com.zerobase.bakingin_project.comment.entity;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.entity.BaseTimeEntity;
import com.zerobase.bakingin_project.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Builder
@Data
@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", updatable = false)
    private Member writer;

    private boolean deleteComment;
}
