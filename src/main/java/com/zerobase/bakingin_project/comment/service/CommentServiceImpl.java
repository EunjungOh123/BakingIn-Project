package com.zerobase.bakingin_project.comment.service;

import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.board.repository.BoardRepository;
import com.zerobase.bakingin_project.comment.dto.CommentDto;
import com.zerobase.bakingin_project.comment.dto.InputComment;
import com.zerobase.bakingin_project.comment.entity.Comment;
import com.zerobase.bakingin_project.comment.exception.CommentErrorCode;
import com.zerobase.bakingin_project.comment.exception.CommentException;
import com.zerobase.bakingin_project.comment.repository.CommentRepository;
import com.zerobase.bakingin_project.member.entity.Member;
import com.zerobase.bakingin_project.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public void add(InputComment input, String userId) {

        Board board = boardRepository.findById(input.getBoardId())
                .orElseThrow(()-> new CommentException(CommentErrorCode.CANNOT_FIND_POST));

        Member user = memberRepository.findById(userId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.CANNOT_WRITE_COMMENT));

        Comment comment = Comment.builder()
                .comment(input.getComment())
                .writer(user)
                .board(board)
                .build();
        commentRepository.save(comment);
    }

    @Override
    public void update(InputComment inputComment, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(CommentErrorCode.CANNOT_REVISE_COMMENT));

        if(!boardRepository.existsById(inputComment.getBoardId())) {
            throw new CommentException(CommentErrorCode.CANNOT_FIND_POST);
        }
        comment.setComment(inputComment.getComment());
        commentRepository.save(comment);
    }

    @Override
    public CommentDto detailComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new CommentException(CommentErrorCode.NOT_EXIST_COMMENT));

        CommentDto commentDto = CommentDto.fromEntity(comment);

        if(!boardRepository.existsById(commentDto.getBoard().getId())) {
            throw new CommentException(CommentErrorCode.CANNOT_FIND_POST);
        }
        return commentDto;
    }

    @Override
    public void delete(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(()-> new CommentException(CommentErrorCode.CANNOT_DELETE_COMMENT));
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> list(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new CommentException(CommentErrorCode.CANNOT_FIND_POST));

        List<Comment> comments = commentRepository.findByBoardOrderByIdAsc(board);
        return comments.stream().map(e ->  CommentDto.fromEntity(e)).collect(Collectors.toList());
    }

    @Override
    public int listCount(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(()-> new CommentException(CommentErrorCode.CANNOT_FIND_POST));
        return commentRepository.countByBoard(board);
    }
}
