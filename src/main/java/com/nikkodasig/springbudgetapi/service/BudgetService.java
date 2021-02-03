package com.nikkodasig.springbudgetapi.service;

import com.nikkodasig.springbudgetapi.dto.BudgetBalanceResponseDto;
import com.nikkodasig.springbudgetapi.dto.BudgetDto;
import com.nikkodasig.springbudgetapi.dto.BudgetResponseDto;
import com.nikkodasig.springbudgetapi.dto.TransactionDto;
import com.nikkodasig.springbudgetapi.dto.mapper.BudgetMapper;
import com.nikkodasig.springbudgetapi.dto.mapper.CategoryMapper;
import com.nikkodasig.springbudgetapi.dto.mapper.TransactionMapper;
import com.nikkodasig.springbudgetapi.exception.NotFoundException;
import com.nikkodasig.springbudgetapi.model.*;
import com.nikkodasig.springbudgetapi.repository.BudgetRepository;
import com.nikkodasig.springbudgetapi.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.*;

@Service
public class BudgetService {

  private BudgetRepository budgetRepository;
  private TransactionRepository transactionRepository;
  private BudgetMapper budgetMapper;
  private CategoryMapper categoryMapper;
  private TransactionMapper transactionMapper;

  public BudgetService(
          BudgetRepository budgetRepository,
          TransactionRepository transactionRepository,
          BudgetMapper budgetMapper,
          CategoryMapper categoryMapper,
          TransactionMapper transactionMapper) {

    this.budgetRepository = budgetRepository;
    this.transactionRepository = transactionRepository;
    this.budgetMapper = budgetMapper;
    this.categoryMapper = categoryMapper;
    this.transactionMapper = transactionMapper;
  }

  public BudgetResponseDto create(BudgetDto budgetDto) {
    Budget savedBudget = budgetRepository.save(budgetMapper.toEntity(budgetDto));
    return budgetMapper.toResponseDto(savedBudget);
  }

  public BudgetResponseDto update(Long id, BudgetDto budgetDto) {
    Budget budgetToUpdate = budgetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    budgetMapper.updateBudgetFromDto(budgetDto, budgetToUpdate);
    Budget savedBudget = budgetRepository.save(budgetToUpdate);

    return budgetMapper.toResponseDto(savedBudget);
  }

  public List<BudgetResponseDto> getAll() {
    return budgetRepository.findAll()
            .stream()
            .map(budget -> budgetMapper.toResponseDto(budget))
            .collect(Collectors.toList());
  }

  public List<BudgetResponseDto> getAllByUser(Long appUserId) {
    return budgetRepository.findAllByUser(appUserId)
            .stream()
            .map(budget -> budgetMapper.toResponseDto(budget))
            .collect(Collectors.toList());
  }

  public BudgetResponseDto getById(Long id) {
    Budget budget = budgetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    return budgetMapper.toResponseDto(budget);
  }

  public List<TransactionDto> getBudgetTransactions(Long id, LocalDate startDate) {
    Budget budget = budgetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    return getTransactionsByBudgetAndPeriod(budget, startDate)
            .stream()
            .map(item -> transactionMapper.toDto(item))
            .collect(Collectors.toList());
  }

  public List<BudgetBalanceResponseDto> getBudgetsBalances(Long appUserId, BudgetPeriodType periodType, LocalDate startDate) {
    List<Budget> budgetList = budgetRepository.findAllByUserAndPeriodType(appUserId, periodType.toString());

    return budgetList
            .stream()
            .map(item -> convertToBudgetBalanceResponseDto(item, startDate))
            .collect(Collectors.toList());
  }

  public void delete(Long id) {
    Budget budget = budgetRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Resource not found"));

    budgetRepository.deleteById(budget.getId());
  }

  private BudgetBalanceResponseDto convertToBudgetBalanceResponseDto(Budget budget, LocalDate startDate) {
    BudgetBalanceResponseDto budgetBalanceResponseDto = new BudgetBalanceResponseDto();
    budgetBalanceResponseDto.setAmountLeft(calculateBudgetBalance(budget, startDate));
    budgetBalanceResponseDto.setId(budget.getId());
    budgetBalanceResponseDto.setAmountLimit(budget.getAmountLimit());
    budgetBalanceResponseDto.setCategoryDto(categoryMapper.toDto(budget.getCategory()));
    budgetBalanceResponseDto.setPeriodType(budget.getPeriodType().name());

    PeriodRange periodRange = getPeriodRange(budget.getPeriodType(), startDate);
    budgetBalanceResponseDto.setStartDate(periodRange.getStartDate());
    budgetBalanceResponseDto.setEndDate(periodRange.getEndDate());

    return budgetBalanceResponseDto;
  }

  private Double calculateBudgetBalance(Budget budget, LocalDate startDate) {
    Double totalExpense = getTransactionsByBudgetAndPeriod(budget, startDate)
            .stream()
            .mapToDouble(item -> item.getAmount())
            .sum();

    return budget.getAmountLimit() - totalExpense;
  }

  private List<Transaction> getTransactionsByBudgetAndPeriod(Budget budget, LocalDate startDate) {
    PeriodRange periodRange = getPeriodRange(budget.getPeriodType(), startDate);

    return transactionRepository.getTransactionsByCategoryAndDateBetween(
            budget.getUser().getId(),
            budget.getCategory().getId(),
            periodRange.getStartDate(),
            periodRange.getEndDate());
  }

  private PeriodRange getPeriodRange(BudgetPeriodType periodType, LocalDate date) {
    PeriodRange periodRange;

    switch (periodType) {
      case WEEKLY:
        periodRange = getWeekPeriodRange(date);
        break;
      case MONTHLY:
        periodRange = getMonthPeriodRange(date);
        break;
      case YEARLY:
        periodRange = getYearPeriodRange(date);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + periodType);
    }

    return periodRange;
  }

  private PeriodRange getWeekPeriodRange(LocalDate date) {
    LocalDate startDate = date.with(WeekFields.of(Locale.US).dayOfWeek(), 1L);
    LocalDate endDate = date.with(WeekFields.of(Locale.US).dayOfWeek(), 7L);

    return new PeriodRange(startDate, endDate);
  }

  private PeriodRange getMonthPeriodRange(LocalDate date) {
    LocalDate startDate = date.with(firstDayOfMonth());
    LocalDate endDate = date.with(lastDayOfMonth());

    return new PeriodRange(startDate, endDate);
  }

  private PeriodRange getYearPeriodRange(LocalDate date) {
    LocalDate startDate = date.with(firstDayOfYear());
    LocalDate endDate = date.with(lastDayOfYear());

    return new PeriodRange(startDate, endDate);
  }

}
