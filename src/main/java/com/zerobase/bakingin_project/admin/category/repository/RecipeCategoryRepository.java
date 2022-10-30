package com.zerobase.bakingin_project.admin.category.repository;

import com.zerobase.bakingin_project.admin.category.entity.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long>  {

    boolean existsByCategoryName (String categoryName);

}
