package com.nikkodasig.springbudgetapi.service;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.dto.mapper.TransactionMapper;
import com.nikkodasig.springbudgetapi.exception.NotFoundException;
import com.nikkodasig.springbudgetapi.model.Transaction;
import com.nikkodasig.springbudgetapi.repository.TransactionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

  public List<TransactionDto> getAllTransactions() {
    List<Transaction> transactionList = transactionRepository.findAll();
    return transactionList
            .stream()
            .map(transaction -> transactionMapper.toDto(transaction))
            .collect(Collectors.toList());
  }

  public TransactionDto getTransaction(Long id) {
    Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Resource not found")));

    return transactionMapper.toDto(transaction);
  }
}
