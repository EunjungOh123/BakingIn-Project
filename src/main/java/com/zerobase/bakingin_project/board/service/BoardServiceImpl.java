package com.zerobase.bakingin_project.board.service;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.admin.category.repository.RecipeCategoryRepository;
import com.zerobase.bakingin_project.board.dto.BoardDto;
import com.zerobase.bakingin_project.board.dto.CKResponse;
import com.zerobase.bakingin_project.board.dto.InputBoard;
import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.board.exception.BoardException;
import com.zerobase.bakingin_project.board.model.BoardParam;
import com.zerobase.bakingin_project.board.model.Pagination;
import com.zerobase.bakingin_project.board.repository.BoardCustomRepository;
import com.zerobase.bakingin_project.board.repository.BoardRepository;
import com.zerobase.bakingin_project.board.exception.BoardErrorCode;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService{

    // 임시로 직접 만든 폴더를 연결해 준다.
    @Value("${ck.image.folder}")
    private String CKImageFolder;

    @Value("${ck.image.path}")
    private String ckImagePath;

    private final BoardRepository boardRepository;
    private final BoardCustomRepository boardCustomRepository;
    private final MemberRepository memberRepository;
    private final RecipeCategoryRepository categoryRepository;

    @Override
    @Transactional
    public void add(InputBoard inputBoard, String userId) {

        Member member = memberRepository.findById(userId)
                .orElseThrow(()-> new BoardException(BoardErrorCode.CANNOT_WRITE_POST));
        RecipeCategory category = categoryRepository.findById(inputBoard.getCategoryId())
                .orElseThrow(()-> new BoardException(BoardErrorCode.CHOOSE_INCORRECT_CATEGORY));

        Board board = Board.builder()
                .title(inputBoard.getTitle())
                .summary(inputBoard.getSummary())
                .ingredient(inputBoard.getIngredient())
                .contents(inputBoard.getContents())
                .deleteBoard(false)
                .writer(member)
                .category(category)
                .build();

        boardRepository.save(board);
        log.info("레시피 등록!");
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
    @Transactional
    public void update(InputBoard inputBoard, Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(BoardErrorCode.CANNOT_FIND_POST));

        RecipeCategory category = categoryRepository.findById(inputBoard.getCategoryId())
                .orElseThrow(()-> new BoardException(BoardErrorCode.CHOOSE_INCORRECT_CATEGORY));
        board.setTitle(inputBoard.getTitle())
                .setSummary(inputBoard.getSummary())
                .setIngredient(inputBoard.getIngredient())
                .setContents(inputBoard.getContents())
                .setCategory(category);

        boardRepository.save(board);
        log.info("레시피 수정!");
    }

    @Override
    public void delete(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(BoardErrorCode.CANNOT_DELETE_POST));
        board.setDeleteBoard(true);
        boardRepository.save(board);
        log.info("레시피 삭제!");
    }

    /**
     * 레시피 삭제 > 스케줄러에 따라 데이터베이스에서 완전히 일괄 삭제
     */
    @Scheduled(cron = "0 0 2 * * *")
    public void deleteBoardInDB () {
        List<Board> boards = boardRepository.findAllByDeleteBoardIsTrue();
        if(!boards.isEmpty()) {
            boardRepository.deleteAll(boards);
            log.info("데이터베이스에서 레시피 일괄 삭제!");
        }
    }
    @Override
    public List<BoardDto> list(Pagination pagination, BoardParam param) {
        // DB select start index
        int startIndex = pagination.getStartIndex();
        // 페이지 당 보여지는 게시글의 최대 개수
        int pageSize = pagination.getPageSize();

        if(param.getSearchType() == null && param.getSearchValue() == null) {
            return boardCustomRepository.boardListPaging(startIndex, pageSize, param)
                    .stream().map(e -> BoardDto.fromEntity(e)).collect(Collectors.toList());
        }
        return boardCustomRepository.searchListPaging(startIndex, pageSize, param)
                    .stream().map(e -> BoardDto.fromEntity(e)).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public int updateViews(Long id) {
        log.info("조회수 증가!");
        return boardRepository.updateView(id);
    }

    @Override
    @Transactional(readOnly = true)
    public BoardDto recipeDetail(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new BoardException(BoardErrorCode.CANNOT_FIND_POST));
        log.info("레시피 세부 조회!");
        return BoardDto.fromEntity(board);
    }
    @Override
    public int totalRecipeBoard (BoardParam boardParam) {
        if(boardParam.getSearchType() != null) {
            return boardCustomRepository.searchAllCnt(boardParam);
        }
        return boardCustomRepository.boardListCnt(boardParam);
    }
}
