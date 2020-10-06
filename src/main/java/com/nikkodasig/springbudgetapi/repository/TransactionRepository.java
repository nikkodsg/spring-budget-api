package com.nikkodasig.springbudgetapi.repository;

import com.nikkodasig.springbudgetapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  @Query(value =
          "SELECT * \n" +
          "FROM transaction t\n" +
          "LEFT JOIN category c\n" +
          "ON t.category_id = c.id\n" +
          "WHERE c.id = ? and t.date >= ? and t.date <= ?", nativeQuery = true)
  List<Transaction> getTransactionByCategoryAndDateBetween(Long categoryId, String startDate, String endDate);

}
