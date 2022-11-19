package com.zerobase.bakingin_project.board.service;

import com.zerobase.bakingin_project.board.dto.BoardDto;
import com.zerobase.bakingin_project.board.dto.CKResponse;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import com.zerobase.bakingin_project.board.model.BoardParam;
import com.zerobase.bakingin_project.board.model.Pagination;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface BoardService {

    /**
     * 레시피 등록
     */
    void add(InputBoard inputBoard, String userId);

    /**
     * 레시피 상세정보
     */
    BoardDto recipeDetail(Long id);

    /**
     * 레시피 본문 이미지
     */
    CKResponse ckImageUpload(MultipartFile upload);

    /**
     * 레시피 수정
     */
    void update(InputBoard inputBoard, Long id);
    /**
     * 레시피 삭제 > deleteBoard(삭제 여부)만 true로
     */
    void delete (Long id);

    /**
     * 레시피 조회수 증가
     */
    int updateViews (Long id);
    /**
     * 레시피 전체 or 카테고리별 목록 보기
     */
    List<BoardDto> list(Pagination pagination, BoardParam boardParam);
    /**
     * 레시피 게시물 총 개수 (전체 or 카테고리별)
     */
    int totalRecipeBoard (BoardParam boardParam);
}
