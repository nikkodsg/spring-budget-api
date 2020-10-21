package com.nikkodasig.springbudgetapi.service;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.dto.mapper.TransactionMapper;
import com.nikkodasig.springbudgetapi.exception.NotFoundException;
import com.nikkodasig.springbudgetapi.model.Transaction;
import com.nikkodasig.springbudgetapi.repository.TransactionRepository;
import org.aspectj.weaver.ast.Not;
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

  public List<TransactionDto> getAll() {
    List<Transaction> transactionList = transactionRepository.findAll();
    return transactionList
            .stream()
            .map(transaction -> transactionMapper.toDto(transaction))
            .collect(Collectors.toList());
  }

  public Page<Transaction> getAllPaginated(LocalDate startDate, LocalDate endDate, Pageable pageable) {
    if (startDate != null && endDate != null) {
      return transactionRepository.findAllByDateBetween(startDate, endDate, pageable);
    }
    if (startDate != null) {
      return transactionRepository.findAllByDateGreaterThanEqual(startDate, pageable);
    }
    if (endDate != null) {
      return transactionRepository.findAllByDateLessThanEqual(endDate, pageable);
    }

    return transactionRepository.findAll(pageable);
  }


  public TransactionDto getTransaction(Long id) {
    Transaction transaction = transactionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(("Resource not found")));

    return transactionMapper.toDto(transaction);
  }

  public void delete(Long id) {
    transactionRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    transactionRepository.deleteById(id);
  }
}
