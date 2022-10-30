package com.zerobase.bakingin_project.admin.category.validator;

import com.zerobase.bakingin_project.admin.category.repository.RecipeCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CheckCategoryName {
    private final RecipeCategoryRepository categoryRepository;

    public boolean validateCategoryName (String name) {
        if(categoryRepository.existsByCategoryName(name)) {
            return false;
        }
        return true;
    }
}
