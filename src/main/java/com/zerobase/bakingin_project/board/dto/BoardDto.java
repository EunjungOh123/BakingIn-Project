package com.zerobase.bakingin_project.board.dto;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.board.entity.Board;
import com.zerobase.bakingin_project.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BoardDto {
    private Long id;
    private Member writer;
    private String title;
    private String summary;
    private String ingredient;
    private String contents;
    private RecipeCategory category;
    private long views;
    private LocalDateTime createAt;

    public static BoardDto fromEntity (Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .category(board.getCategory())
                .writer(board.getWriter())
                .title(board.getTitle())
                .summary(board.getSummary())
                .ingredient(board.getIngredient())
                .contents(board.getContents())
                .createAt(board.getCreateAt())
                .views(board.getViews())
                .build();
    }
}
