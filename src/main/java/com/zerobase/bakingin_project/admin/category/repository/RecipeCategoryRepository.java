package com.zerobase.bakingin_project.admin.category.repository;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long>  {

    boolean existsByCategoryName (String categoryName);

    List <RecipeCategory> findAllByUsingCategoryIsTrue();

}
