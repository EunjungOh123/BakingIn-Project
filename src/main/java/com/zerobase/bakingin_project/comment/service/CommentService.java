package com.zerobase.bakingin_project.comment.service;

import com.zerobase.bakingin_project.comment.dto.CommentDto;
import com.zerobase.bakingin_project.comment.dto.InputComment;

import java.util.List;

public interface CommentService {
    /**
     * 댓글 등록
     */
    void add (InputComment inputComment, String userId);
    /**
     * 댓글 수정
     */
    void update (InputComment inputComment, Long commentId);
    /**
     * 댓글 상세 보기
     */
    CommentDto detailComment (Long commentId);
    /**
     * 댓글 삭제 > deleteComment true로 변경
     */
    void delete (Long id);
    /**
     * 레시피마다 댓글 목록 보기
     */
    List<CommentDto> list (Long boardId);
    /**
     * 각 레시피의 댓글 개수
     */
    int listCount (Long boardId);
}
