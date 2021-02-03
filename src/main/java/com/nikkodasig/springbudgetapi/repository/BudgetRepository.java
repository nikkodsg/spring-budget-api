package com.nikkodasig.springbudgetapi.repository;

import com.nikkodasig.springbudgetapi.model.Budget;
import com.nikkodasig.springbudgetapi.model.BudgetPeriodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

  @Query(value = "SELECT * FROM budget WHERE app_user_id = :app_user_id", nativeQuery = true)
  List<Budget> findAllByUser(@Param("app_user_id") Long appUserId);

  @Query(value = "SELECT * FROM budget WHERE app_user_id = :appUserId AND period_type = :periodType", nativeQuery = true)
  List<Budget> findAllByUserAndPeriodType(@Param("appUserId") Long appUserId, @Param("periodType") String periodType);

}
