package com.zerobase.bakingin_project.board.dto;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.member.entity.Member;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InputBoard {
    private String title;
    private String summary;
    private String ingredient;
    private String contents;
    private Member writer;
    private Long categoryId;
    private RecipeCategory category;
}
