package com.nikkodasig.springbudgetapi.dto;

import java.time.LocalDate;

public class BudgetBalanceResponseDto extends BudgetResponseDto {

  private Double amountLeft;
  private LocalDate startDate;
  private LocalDate endDate;

  public Double getAmountLeft() {
    return amountLeft;
  }

  public void setAmountLeft(Double amountLeft) {
    this.amountLeft = amountLeft;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
