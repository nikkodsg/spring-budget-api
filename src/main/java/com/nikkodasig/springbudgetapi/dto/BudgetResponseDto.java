package com.nikkodasig.springbudgetapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BudgetResponseDto {

  private Long id;
  private Double amountLimit;
  private String periodType;
  private CategoryDto categoryDto;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getAmountLimit() {
    return amountLimit;
  }

  public void setAmountLimit(Double amountLimit) {
    this.amountLimit = amountLimit;
  }

  public String getPeriodType() {
    return periodType;
  }

  public void setPeriodType(String periodType) {
    this.periodType = periodType;
  }

  @JsonProperty("category")
  public CategoryDto getCategoryDto() {
    return categoryDto;
  }

  public void setCategoryDto(CategoryDto categoryDto) {
    this.categoryDto = categoryDto;
  }
}
