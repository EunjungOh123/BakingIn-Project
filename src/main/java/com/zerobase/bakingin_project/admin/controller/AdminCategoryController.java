package com.zerobase.bakingin_project.admin.controller;

import com.zerobase.bakingin_project.admin.category.dto.InputRecipeCategory;
import com.zerobase.bakingin_project.admin.category.dto.RecipeCategoryDto;
import com.zerobase.bakingin_project.admin.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/recipe-list")
    public String categoryList(Model model) {

        List<RecipeCategoryDto> list = categoryService.list();
        model.addAttribute("list", list);

        return "admin/category/recipe-list";
    }
    @PostMapping("/add")
    public String add(InputRecipeCategory categoryDto) {

        categoryService.add(categoryDto);

        return "redirect:/admin/category/recipe-list";
    }

    @PostMapping("/update")
    public String update(InputRecipeCategory categoryDto) {
        categoryService.update(categoryDto);
        return "redirect:/admin/category/recipe-list";
    }

    @PostMapping("/delete")
    public String delete(InputRecipeCategory categoryDto) {

        categoryService.delete(categoryDto.getId());

        return "redirect:/admin/category/recipe-list";
    }
}
