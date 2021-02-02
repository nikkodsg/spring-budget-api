package com.nikkodasig.springbudgetapi.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Budget {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "amount_limit")
  private Double amountLimit;

  @Enumerated(value = EnumType.STRING)
  private BudgetPeriodType periodType;

  @OneToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @OneToOne
  @JoinColumn(name = "app_user_id")
  private User user;

  @CreatedDate
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

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

  public BudgetPeriodType getPeriodType() {
    return periodType;
  }

  public void setPeriodType(BudgetPeriodType periodType) {
    this.periodType = periodType;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

}
