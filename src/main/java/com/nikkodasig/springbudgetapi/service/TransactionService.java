package com.nikkodasig.springbudgetapi.service;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.dto.TransactionResponse;
import com.nikkodasig.springbudgetapi.dto.mapper.TransactionMapper;
import com.nikkodasig.springbudgetapi.exception.NotFoundException;
import com.nikkodasig.springbudgetapi.model.CategoryType;
import com.nikkodasig.springbudgetapi.model.Transaction;
import com.nikkodasig.springbudgetapi.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

  private TransactionRepository transactionRepository;
  private TransactionMapper transactionMapper;

  public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
    this.transactionRepository = transactionRepository;
    this.transactionMapper = transactionMapper;
  }

  public TransactionDto save(TransactionDto transactionDto) {
    Transaction transaction = transactionMapper.toEntity(transactionDto);
    Transaction newTransaction = transactionRepository.save(transaction);
    return transactionMapper.toDto(newTransaction);
  }

  public TransactionDto updateTransaction(Long id, TransactionDto transactionDto) {
    Transaction transaction = transactionRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    transactionMapper.updateTransactionFromDto(transactionDto, transaction);
    Transaction updatedTransaction = transactionRepository.save(transaction);

    return transactionMapper.toDto(updatedTransaction);
  }

  public List<TransactionResponse> getAll() {
    List<Transaction> transactionList = transactionRepository.findAll();
    return transactionList
            .stream()
            .map(transaction -> transactionMapper.toResponse(transaction))
            .collect(Collectors.toList());
  }

  public Page<Transaction> getAllPaginated(Long appUserId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
    if (startDate != null && endDate != null) {
      return transactionRepository.findAllByUserAndDateBetween(appUserId, startDate, endDate, pageable);
    }

    if (startDate != null) {
      return transactionRepository.findAllByUserAndDateGreaterThanEqual(appUserId, startDate, pageable);
    }

    if (endDate != null) {
      return transactionRepository.findAllByUserAndDateLessThanEqual(appUserId, endDate, pageable);
    }

    return transactionRepository.findAllByUser(appUserId, pageable);
  }


  public TransactionResponse getTransaction(Long id) {
    Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Resource not found")));

    return transactionMapper.toResponse(transaction);
  }

  public void delete(Long id) {
    transactionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    transactionRepository.deleteById(id);
  }

  public double getTotalAmount(Long appUserId, LocalDate startDate, LocalDate endDate, String categoryType) {
    return transactionRepository.getSumOfAmountByCategoryType(appUserId, startDate, endDate, categoryType).orElse(0.0d);
  }
}
