package com.zerobase.bakingin_project.board.service;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.admin.category.repository.RecipeCategoryRepository;
import com.zerobase.bakingin_project.board.dto.BoardDto;
import com.zerobase.bakingin_project.board.dto.CKResponse;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.board.exception.BoardException;
import com.zerobase.bakingin_project.board.repository.BoardRepository;
import com.zerobase.bakingin_project.board.type.BoardErrorCode;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    // 임시로 직접 만든 폴더를 연결해 준다.
    @Value("${ck.image.folder}")
    private String CKImageFolder;

    @Value("${ck.image.path}")
    private String ckImagePath;

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final RecipeCategoryRepository categoryRepository;

    @Override
    @Transactional
    public void add(InputBoard inputBoard, String userId) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BoardException(BoardErrorCode.CANNOT_WRITE_POST));
        RecipeCategory category = categoryRepository.findById(inputBoard.getCategoryId())
                .orElseThrow(()-> new BoardException(BoardErrorCode.CHOOSE_INCORRECT_CATEGORY));
        
        Board board = inputBoard.toEntity();
        board.setWriter(member);
        board.setCategory(category);
        boardRepository.save(board);
    }

    @Override
    public CKResponse ckImageUpload(MultipartFile image) {
        if(image != null && !image.isEmpty()) {
            // 파일명 바꾸기
            String imageName = UUID.randomUUID()+ "-" + image.getOriginalFilename();
            // 하드디스크에 이미지를 저장할 파일을 생성
            File file = new File(CKImageFolder, imageName);
            // 이미지를 파일로 이동
            try {
                image.transferTo(file);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            return new CKResponse(1, imageName, ckImagePath + imageName);
        }
        return null;
    }

    @Override
    public void update(InputBoard inputBoard, Long id) {

    }


    @Override
    @Transactional
    public int updateViews(Long id) {
        return boardRepository.updateView(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardDto recipeDetail(long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(BoardErrorCode.CANNOT_FIND_POST));
        return BoardDto.fromEntity(board);
    }

    @Override
    public Page<Board> boardList(Pageable pageable) {

        Page<Board> boards = boardRepository.findAll(pageable);

        return boards;
    }
}
