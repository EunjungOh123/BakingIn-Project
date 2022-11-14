package com.zerobase.bakingin_project.admin.category.dto;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import lombok.Data;

@Data
public class InputRecipeCategory {

    Long id;
    String categoryName;
    boolean usingCategory;


    public RecipeCategory toEntity() {
        return RecipeCategory.builder()
                .categoryName(categoryName)
                .usingCategory(true)
                .build();
    }
}
