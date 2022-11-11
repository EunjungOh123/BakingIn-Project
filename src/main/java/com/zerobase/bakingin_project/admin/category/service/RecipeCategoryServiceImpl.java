package com.zerobase.bakingin_project.admin.category.service;

import com.zerobase.bakingin_project.admin.category.dto.InputRecipeCategory;
import com.zerobase.bakingin_project.admin.category.dto.RecipeCategoryDto;
import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.admin.category.repository.RecipeCategoryRepository;
import com.zerobase.bakingin_project.admin.exception.CategoryErrorCode;
import com.zerobase.bakingin_project.admin.exception.CategoryException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class RecipeCategoryServiceImpl implements CategoryService {

    private final RecipeCategoryRepository categoryRepository;

    private Sort getSortBySortValueDesc() {
        return Sort.by(Sort.Direction.DESC, "createAt"); // 내림차순 정렬
    }

    @Override
    public List<RecipeCategoryDto> list() {

        List<RecipeCategory> categories = categoryRepository.findAll(getSortBySortValueDesc());

        return RecipeCategoryDto.fromEntity(categories);
    }

    @Override
    public List<RecipeCategoryDto> frontList() {
        List<RecipeCategory> categories = categoryRepository.findAllByUsingCategoryIsTrue();

        return RecipeCategoryDto.fromEntity(categories);
    }

    @Override
    public void add(InputRecipeCategory inputRecipeCategory) {
        RecipeCategory recipeCategory = inputRecipeCategory.toEntity();

        if (categoryRepository.existsByCategoryName(inputRecipeCategory.getCategoryName())) {
            throw new CategoryException(CategoryErrorCode.CATEGORY_NAME_ALREADY_EXIST);
        }

        categoryRepository.save(recipeCategory);
    }

    @Override
    public void update(InputRecipeCategory inputRecipeCategory) {
        RecipeCategory category = categoryRepository.findById(inputRecipeCategory.getId())
                .orElseThrow(() -> new CategoryException(CategoryErrorCode.CATEGORY_NOT_EXIST));

        category.setCategoryName(inputRecipeCategory.getCategoryName());
        category.setUsingCategory(inputRecipeCategory.isUsingCategory());
        categoryRepository.save(category);

    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
