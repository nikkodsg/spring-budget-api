package com.nikkodasig.springbudgetapi.controller;

import com.nikkodasig.springbudgetapi.dto.BudgetBalanceResponseDto;
import com.nikkodasig.springbudgetapi.dto.BudgetDto;
import com.nikkodasig.springbudgetapi.dto.BudgetResponseDto;
import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.model.BudgetPeriodType;
import com.nikkodasig.springbudgetapi.service.BudgetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/budgets")
public class BudgetController {

  private BudgetService budgetService;

  public BudgetController(BudgetService budgetService) {
    this.budgetService = budgetService;
  }

  @PostMapping
  public ResponseEntity<BudgetResponseDto> createBudget(@Valid @RequestBody BudgetDto budgetDto) {
    return ResponseEntity.ok(budgetService.create(budgetDto)) ;
  }

  @GetMapping
  public ResponseEntity<List<BudgetResponseDto>> getBudgets() {
    return ResponseEntity.ok(budgetService.getAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<BudgetResponseDto> updateBudget(@Valid @PathVariable Long id, @RequestBody BudgetDto budgetDto) {
    return ResponseEntity.ok(budgetService.update(id, budgetDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<BudgetResponseDto> getBudget(@PathVariable Long id) {
    return ResponseEntity.ok(budgetService.getById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteBudget(@PathVariable Long id) {
    budgetService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{id}/transactions")
  public ResponseEntity<List<TransactionDto>> getBudgetTransactions(@PathVariable Long id,
                                                                    @RequestParam String startDate) {

    return ResponseEntity.ok(budgetService.getBudgetTransactions(id, LocalDate.parse(startDate)));
  }

  @GetMapping("/balances")
  public ResponseEntity<List<BudgetBalanceResponseDto>> getBudgetsWithBalances(
          @RequestParam String periodType,
          @RequestParam String startDate,
          @RequestParam(required = false) String endDate) {

    // TODO: catch IllegalArgumentException
    BudgetPeriodType budgetPeriodType = BudgetPeriodType.valueOf(periodType.toUpperCase());

    return ResponseEntity.ok(budgetService.getBudgetsBalances(budgetPeriodType, LocalDate.parse(startDate)));
  }

}
