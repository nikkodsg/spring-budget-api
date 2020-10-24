package com.nikkodasig.springbudgetapi.repository;

import com.nikkodasig.springbudgetapi.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Page<Transaction> findAll(Pageable pageable);

  Page<Transaction> findAllByDateGreaterThanEqual(LocalDate startDate, Pageable pageable);

  Page<Transaction> findAllByDateLessThanEqual(LocalDate endDate, Pageable pageable);

  Page<Transaction> findAllByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

  @Query(value =
          "SELECT * \n" +
          "FROM transaction t\n" +
          "LEFT JOIN category c\n" +
          "ON t.category_id = c.id\n" +
          "WHERE c.id = ? and t.date >= ? and t.date <= ?", nativeQuery = true)
  List<Transaction> getTransactionByCategoryAndDateBetween(Long categoryId, LocalDate startDate, LocalDate endDate);

}
