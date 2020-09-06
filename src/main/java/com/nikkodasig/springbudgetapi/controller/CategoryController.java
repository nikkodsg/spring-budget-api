package com.nikkodasig.springbudgetapi.controller;

import com.nikkodasig.springbudgetapi.dto.CategoryDto;
import com.nikkodasig.springbudgetapi.model.Category;
import com.nikkodasig.springbudgetapi.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping
  public List<Category> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @GetMapping("/{id}")
  public CategoryDto getCategory(@PathVariable Long id) {
    return categoryService.getCategory(id);
  }
}
