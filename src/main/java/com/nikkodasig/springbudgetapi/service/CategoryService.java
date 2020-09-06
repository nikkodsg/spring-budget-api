package com.nikkodasig.springbudgetapi.service;

import com.nikkodasig.springbudgetapi.dto.CategoryDto;
import com.nikkodasig.springbudgetapi.dto.mapper.CategoryMapper;
import com.nikkodasig.springbudgetapi.exception.NotFoundException;
import com.nikkodasig.springbudgetapi.model.Category;
import com.nikkodasig.springbudgetapi.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

  private CategoryRepository categoryRepository;
  private CategoryMapper categoryMapper;

  public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
    this.categoryRepository = categoryRepository;
    this.categoryMapper = categoryMapper;
  }

  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  public CategoryDto getCategory(Long id) {
    Category category = categoryRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    return categoryMapper.toDto(category);
  }
}
