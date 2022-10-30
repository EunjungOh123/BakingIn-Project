package com.zerobase.bakingin_project.admin.category.dto;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import lombok.Data;

@Data
public class InputRecipeCategory {

    Long id;
    String categoryName;
    boolean usingYn;


    public RecipeCategory toEntity() {

        return RecipeCategory.builder()
                .categoryName(categoryName)
                .usingYn(true)
                .build();
    }
}
