package com.zerobase.bakingin_project.admin.category.dto;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeCategoryDto {

    private Long id;
    private String categoryName;
    private boolean usingCategory;
    private LocalDateTime createAt;

    public static List<RecipeCategoryDto> fromEntity (List<RecipeCategory> categories) {
        if (categories != null) {
            List<RecipeCategoryDto> categoryList = new ArrayList<>();
            for(RecipeCategory x : categories) {
                categoryList.add(fromEntity(x));
            }
            return categoryList;
        }
        return null;
    }

    public static RecipeCategoryDto fromEntity (RecipeCategory category) {
        return RecipeCategoryDto.builder()
                .id(category.getId())
                .categoryName(category.getCategoryName())
                .usingCategory(category.isUsingCategory())
                .createAt(category.getCreateAt())
                .build();
    }

    public String getCreateAtText() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
        return createAt != null ? createAt.format(formatter) : "";
    }
}
