package com.nikkodasig.springbudgetapi.repository;

import com.nikkodasig.springbudgetapi.model.Budget;
import com.nikkodasig.springbudgetapi.model.BudgetPeriodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

  List<Budget> findAllByPeriodType(BudgetPeriodType periodType);

}
