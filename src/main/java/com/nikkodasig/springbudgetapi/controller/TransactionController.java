package com.nikkodasig.springbudgetapi.controller;

import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.dto.TransactionResponse;
import com.nikkodasig.springbudgetapi.dto.mapper.TransactionMapper;
import com.nikkodasig.springbudgetapi.model.CategoryType;
import com.nikkodasig.springbudgetapi.model.Transaction;
import com.nikkodasig.springbudgetapi.model.User;
import com.nikkodasig.springbudgetapi.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

  private TransactionService transactionService;
  private TransactionMapper transactionMapper;

  public TransactionController(TransactionService transactionService, TransactionMapper transactionMapper) {
    this.transactionService = transactionService;
    this.transactionMapper = transactionMapper;
  }

  @PostMapping
  public ResponseEntity<TransactionDto> createTransaction(@Valid @RequestBody TransactionDto transactionDto,
                                                          Authentication authentication) {

    User currentUser = (User) authentication.getPrincipal();
    transactionDto.setAppUserId(currentUser.getId());
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
  public List<TransactionResponse> getTransactions() {
    return transactionService.getAll();
  }

  @GetMapping("/paged")
  public ResponseEntity<Map<String, Object>> getTransactions(
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
          @RequestParam(required = false, defaultValue = "0") Integer page,
          @RequestParam(required = false, defaultValue = "20") Integer pageSize,
          Authentication authentication) {

    User currentUser = (User) authentication.getPrincipal();

    Pageable pageable = PageRequest.of(page, pageSize, Sort.by("date").descending().and(Sort.by("id").descending()));
    Page<Transaction> transactionPage = transactionService.getAllPaginated(currentUser.getId(), startDate, endDate, pageable);
    List<TransactionResponse> transactions = transactionPage.getContent()
            .stream()
            .map(transaction -> transactionMapper.toResponse(transaction))
            .collect(Collectors.toList());

    Map<String, Object> response = new LinkedHashMap<>();
    response.put("transactions", transactions);
    response.put("pageNumber", transactionPage.getNumber());
    response.put("pageSize", transactionPage.getSize());
    response.put("totalItems", transactionPage.getTotalElements());
    response.put("totalPages", transactionPage.getTotalPages());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getTransaction(@PathVariable Long id) {
    TransactionResponse transaction = transactionService.getTransaction(id);
    return ResponseEntity.ok().body(transaction);
  }

  @GetMapping("/total")
  public ResponseEntity<?> getTotalAmount(
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
          @RequestParam String categoryType,
          Authentication authentication) {

    User currentUser = (User) authentication.getPrincipal();

    double totalAmount = transactionService.getTotalAmount(currentUser.getId(), startDate, endDate, categoryType);

    Map<String, Double> response = new LinkedHashMap<>();
    response.put("totalAmount", totalAmount);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
    transactionService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
