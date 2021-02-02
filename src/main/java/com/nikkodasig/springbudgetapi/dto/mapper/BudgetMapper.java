package com.nikkodasig.springbudgetapi.dto.mapper;

import com.nikkodasig.springbudgetapi.dto.BudgetDto;
import com.nikkodasig.springbudgetapi.dto.BudgetResponseDto;
import com.nikkodasig.springbudgetapi.model.Budget;
import com.nikkodasig.springbudgetapi.repository.CategoryRepository;
import com.nikkodasig.springbudgetapi.repository.UserRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {CategoryRepository.class, UserRepository.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface BudgetMapper {

  @Mapping(source = "category.id", target = "categoryId")
  BudgetDto toDto(Budget budget);

  @Mapping(source = "categoryId", target = "category")
  @Mapping(source = "appUserId", target = "user")
  Budget toEntity(BudgetDto budgetDto);

  @Mapping(source = "budget.category", target = "categoryDto")
  BudgetResponseDto toResponseDto(Budget budget);

  @Mapping(source = "categoryId", target = "category")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateBudgetFromDto(BudgetDto budgetDto, @MappingTarget Budget budget);

}
