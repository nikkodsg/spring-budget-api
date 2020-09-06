package com.nikkodasig.springbudgetapi.dto.mapper;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.model.Transaction;
import com.nikkodasig.springbudgetapi.repository.CategoryRepository;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CategoryRepository.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TransactionMapper {

  @Mapping(source = "category.id", target = "categoryId")
  TransactionDto toDto(Transaction transaction);

  @Mapping(source = "categoryId", target = "category")
  Transaction toEntity(TransactionDto transactionDto);

  @Mapping(source = "categoryId", target = "category")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateTransactionFromDto(TransactionDto dto, @MappingTarget Transaction entity);

}
