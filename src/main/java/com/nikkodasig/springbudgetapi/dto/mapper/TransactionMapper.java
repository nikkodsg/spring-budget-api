package com.nikkodasig.springbudgetapi.dto.mapper;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.dto.TransactionResponse;
import com.nikkodasig.springbudgetapi.model.Transaction;
import com.nikkodasig.springbudgetapi.repository.CategoryRepository;
import com.nikkodasig.springbudgetapi.repository.UserRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryRepository.class, UserRepository.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransactionMapper {

  @Mapping(source = "category.id", target = "categoryId")
  @Mapping(source = "user.id", target = "appUserId")
  TransactionDto toDto(Transaction transaction);

  @Mapping(source = "category", target = "category")
  TransactionResponse toResponse(Transaction transaction);

  @Mapping(source = "categoryId", target = "category")
  @Mapping(source = "appUserId", target = "user")
  Transaction toEntity(TransactionDto transactionDto);

  @Mapping(source = "categoryId", target = "category")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTransactionFromDto(TransactionDto dto, @MappingTarget Transaction entity);

}
