package com.zerobase.bakingin_project.board.service;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.admin.category.repository.RecipeCategoryRepository;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
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
    @Transactional(rollbackFor = {IllegalStateException.class, IOException.class })
    public void add(InputBoard inputBoard, String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        Optional<RecipeCategory> optionalCategory = categoryRepository.findById(inputBoard.getCategoryId());

        if(!optionalMember.isPresent()) {
            throw new BoardException(BoardErrorCode.CANNOT_WRITE_POST);
        }

        if(!optionalCategory.isPresent()) {
            throw new BoardException(BoardErrorCode.CANNOT_WRITE_POST);
        }

        Member member = optionalMember.get();
        RecipeCategory category = optionalCategory.get();
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
}
