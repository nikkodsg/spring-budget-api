package com.nikkodasig.springbudgetapi.dto.mapper;

import com.nikkodasig.springbudgetapi.dto.CategoryDto;
import com.nikkodasig.springbudgetapi.model.Category;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CategoryMapper {

  CategoryDto toDto(Category entity);
  Category toEntity(CategoryDto dto);

}
