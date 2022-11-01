package com.zerobase.bakingin_project.board.exception;

import com.zerobase.bakingin_project.board.type.BoardErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardException extends RuntimeException {
    private BoardErrorCode boardErrorCode;
    private String message;

    public BoardException(BoardErrorCode boardErrorCode){
        this.boardErrorCode = boardErrorCode;
        this.message = boardErrorCode.getValue();
    }
}
