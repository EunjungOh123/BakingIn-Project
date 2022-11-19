package com.zerobase.bakingin_project.comment.dto;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.comment.entity.Comment;
import com.zerobase.bakingin_project.member.entity.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class CommentDto {
    private Long id;
    private String comment;
    private Member writer;
    private Board board;
    private boolean deleteComment;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public static CommentDto fromEntity (Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .comment(comment.getComment())
                .deleteComment(comment.isDeleteComment())
                .createAt(comment.getCreateAt())
                .updateAt(comment.getUpdateAt())
                .writer(comment.getWriter())
                .board(comment.getBoard())
                .build();
    }
}
