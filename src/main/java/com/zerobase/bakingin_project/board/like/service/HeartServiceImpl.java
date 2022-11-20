package com.zerobase.bakingin_project.board.like.service;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.board.like.entity.Heart;
import com.zerobase.bakingin_project.board.like.exception.HeartErrorCode;
import com.zerobase.bakingin_project.board.like.exception.HeartException;
import com.zerobase.bakingin_project.board.like.repository.HeartRepository;
import com.zerobase.bakingin_project.board.repository.BoardRepository;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService{

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public void postHeart(Long boardId, String userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new HeartException(HeartErrorCode.CANNOT_FIND_POST));

        Member user = memberRepository.findById(userId)
                .orElseThrow(()-> new HeartException(HeartErrorCode.CANNOT_FIND_USER));

        if(userId.equals(board.getWriter().getUserId())) {
            throw new HeartException(HeartErrorCode.CANNOT_REGISTER_LIKE_MINE);
        }

        Optional<Heart> optionalHeart = heartRepository.findByBoardAndUser(board, user);
        if(optionalHeart.isEmpty()) {
            heartRepository.save(Heart.builder().board(board).user(user).build());
        } else {
            heartRepository.delete(optionalHeart.get());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public int countHeartByBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new HeartException(HeartErrorCode.CANNOT_FIND_POST));
        return heartRepository.countByBoard(board);
    }
}
