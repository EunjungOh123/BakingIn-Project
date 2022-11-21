package com.zerobase.bakingin_project.board.like.service;

public interface HeartService {

    /**
     * 좋아요 등록 or 취소
     */
    void postHeart(Long boardId, String userId);

    /**
     * 각 레시피의 좋아요 개수
     */
    int countHeartByBoard (Long boardId);
}
