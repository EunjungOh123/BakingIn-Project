package com.zerobase.bakingin_project.admin.category.service;

import com.zerobase.bakingin_project.admin.category.dto.InputRecipeCategory;
import com.zerobase.bakingin_project.admin.category.dto.RecipeCategoryDto;

import java.util.List;

public interface CategoryService {

    List<RecipeCategoryDto> list();

    /**
     * 카테고리 추가
     */
    void add(InputRecipeCategory inputRecipeCategory);

    /**
     * 카테고리 수정
     */
    void update (InputRecipeCategory inputRecipeCategory);
    /**
     *  카테고리 삭제
     */
    void delete (long id);
}
