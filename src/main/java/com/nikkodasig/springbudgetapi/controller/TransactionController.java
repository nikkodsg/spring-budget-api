package com.nikkodasig.springbudgetapi.controller;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  private TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping
  public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {
    TransactionDto newTransactionDto = transactionService.save(transactionDto);
    return new ResponseEntity<>(newTransactionDto, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<TransactionDto> updateTransaction(@PathVariable Long id,
                                                          @RequestBody TransactionDto transactionDto) {

    TransactionDto updatedTransactionDto = transactionService.updateTransaction(id, transactionDto);
    return ResponseEntity.ok(updatedTransactionDto);
  }

  @GetMapping
  public List<TransactionDto> getTransactions() {
    return transactionService.getAllTransactions();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTransactions(@PathVariable Long id) {
    TransactionDto transactionDto = transactionService.getTransaction(id);
    return ResponseEntity.ok().body(transactionDto);
  }

}
