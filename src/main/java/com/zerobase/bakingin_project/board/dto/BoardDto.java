package com.zerobase.bakingin_project.board.dto;

import com.zerobase.bakingin_project.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDto {
    private Long id;
    private String title;
    private String summary;
    private String ingredient;
    private String contents;
    private long views;
    private LocalDateTime createAt;

    public static BoardDto fromEntity (Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .summary(board.getSummary())
                .ingredient(board.getIngredient())
                .contents(board.getContents())
                .createAt(board.getCreateAt())
                .views(board.getViews())
                .build();
    }

    public String getCreateAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return createAt != null ? createAt.format(formatter) : "";
    }
}
