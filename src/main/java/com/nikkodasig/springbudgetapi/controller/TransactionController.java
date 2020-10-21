package com.nikkodasig.springbudgetapi.controller;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.model.Transaction;
import com.nikkodasig.springbudgetapi.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
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
    return transactionService.getAll();
  }

  @GetMapping("/paged")
  public ResponseEntity<Map<String, Object>> getTransactions(
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
          @RequestParam(required = false, defaultValue = "0") Integer page,
          @RequestParam(required = false, defaultValue = "20") Integer pageSize) {

    Pageable pageable = PageRequest.of(page, pageSize, Sort.by("date").descending().and(Sort.by("id").descending()));
    Page<Transaction> transactionPage = transactionService.getAllPaginated(startDate, endDate, pageable);
    List<Transaction> transactionList = transactionPage.getContent();

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("transactions", transactionList);
    response.put("pageNumber", transactionPage.getNumber());
    response.put("pageSize", transactionPage.getSize());
    response.put("totalItems", transactionPage.getTotalElements());
    response.put("totalPages", transactionPage.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTransactions(@PathVariable Long id) {
    TransactionDto transactionDto = transactionService.getTransaction(id);
    return ResponseEntity.ok().body(transactionDto);
  }

}
