package com.zerobase.bakingin_project.board.service;

import com.zerobase.bakingin_project.board.dto.CKResponse;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import org.springframework.web.multipart.MultipartFile;

public interface BoardService {

    /**
     * 레시피 등록
     */
    void add(InputBoard inputBoard, String userId);

    /**
     * 레시피 수정
     */


    /**
     * 레시피 목록
     */

    /**
     * 레시피 상세정보
     */

    /**
     * 레시피 삭제
     */


    /**
     * 레시피 이미지
     */
    CKResponse ckImageUpload(MultipartFile upload);
}
