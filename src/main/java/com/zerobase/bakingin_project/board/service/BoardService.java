package com.zerobase.bakingin_project.board.service;

import com.zerobase.bakingin_project.board.dto.BoardDto;
import com.zerobase.bakingin_project.board.dto.CKResponse;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import org.springframework.web.multipart.MultipartFile;


public interface BoardService {

    /**
     * 레시피 등록
     */
    void add(InputBoard inputBoard, String userId);

    /**
     * 레시피 상세정보
     */
    BoardDto recipeDetail(long id);

    /**
     * 레시피 본문 이미지
     */
    CKResponse ckImageUpload(MultipartFile upload);

    /**
     * 레시피 수정
     */
    void update(InputBoard inputBoard, Long id);
    /**
     * 레시피 삭제
     */

    /**
     * 레시피 조회수 증가
     */
    int updateViews (Long id);
}
