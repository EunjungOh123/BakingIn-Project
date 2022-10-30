package com.zerobase.bakingin_project.admin.controller;

import com.zerobase.bakingin_project.admin.category.dto.InputRecipeCategory;
import com.zerobase.bakingin_project.admin.category.dto.RecipeCategoryDto;
import com.zerobase.bakingin_project.admin.category.service.CategoryService;
import com.zerobase.bakingin_project.admin.category.validator.CheckCategoryName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;
    private final CheckCategoryName checkCategoryName;

    @GetMapping("/admin/category/list")
    public String categoryList(Model model) {

        List<RecipeCategoryDto> list = categoryService.list();
        model.addAttribute("list", list);

        return "admin/category/recipe-list";
    }
    @PostMapping("/admin/category/add")
    public String add(InputRecipeCategory categoryDto) {

        boolean result = checkCategoryName.validateCategoryName(categoryDto.getCategoryName());

        if(result) {
            categoryService.add(categoryDto);
        }

        return "redirect:/admin/category/list";
    }
}
