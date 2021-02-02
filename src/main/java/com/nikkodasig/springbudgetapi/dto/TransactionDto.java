package com.nikkodasig.springbudgetapi.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TransactionDto {

  private Long id;

  @NotNull
  private Double amount;

  @NotBlank
  private String description;

  @NotBlank
  private String date;

  @NotNull
  private Long categoryId;

  private Long appUserId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public Long getAppUserId() {
    return appUserId;
  }

  public void setAppUserId(Long appUserId) {
    this.appUserId = appUserId;
  }
}
