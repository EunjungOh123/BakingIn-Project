package com.zerobase.bakingin_project.admin.category.service;

import com.zerobase.bakingin_project.admin.category.dto.InputRecipeCategory;
import com.zerobase.bakingin_project.admin.category.dto.RecipeCategoryDto;
import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import com.zerobase.bakingin_project.admin.category.repository.RecipeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeCategoryServiceImpl implements CategoryService{

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
    public void add(InputRecipeCategory inputRecipeCategory) {
        RecipeCategory recipeCategory = inputRecipeCategory.toEntity();
        categoryRepository.save(recipeCategory);
    }

    @Override
    public void update(InputRecipeCategory inputRecipeCategory) {
        Optional<RecipeCategory> optionalRecipeCategory = categoryRepository.findById(inputRecipeCategory.getId());

        if(optionalRecipeCategory.isPresent()) {
            RecipeCategory category = optionalRecipeCategory.get();
            category.setCategoryName(inputRecipeCategory.getCategoryName());
            category.setUsingYn(inputRecipeCategory.isUsingYn());
            categoryRepository.save(category);
        }
    }

    @Override
    public void delete(long id) {
        categoryRepository.deleteById(id);
    }
}
