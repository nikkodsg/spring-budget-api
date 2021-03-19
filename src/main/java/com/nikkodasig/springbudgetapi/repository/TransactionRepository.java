package com.nikkodasig.springbudgetapi.repository;

import com.nikkodasig.springbudgetapi.model.CategoryType;
import com.nikkodasig.springbudgetapi.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Page<Transaction> findAll(Pageable pageable);

  @Query(
          value = "SELECT * FROM transaction t WHERE t.app_user_id = :appUserId AND t.date >= :startDate",
          countQuery = "SELECT COUNT(*) FROM transaction t WHERE t.app_user_id = :appUserId AND t.date >= :startDate",
          nativeQuery = true)
  Page<Transaction> findAllByUserAndDateGreaterThanEqual(@Param("appUserId") Long appUserId,
                                                         @Param("startDate") LocalDate startDate,
                                                         Pageable pageable);

  @Query(
          value = "SELECT * FROM transaction t WHERE t.app_user_id = :appUserId AND t.date <= :endDate",
          countQuery = "SELECT COUNT(*) FROM transaction t WHERE t.app_user_id = :appUserId AND t.date <= :endDate",
          nativeQuery = true)
  Page<Transaction> findAllByUserAndDateLessThanEqual(@Param("appUserId") Long appUserId,
                                                      @Param("endDate") LocalDate endDate,
                                                      Pageable pageable);

  @Query(
          value = "SELECT * FROM transaction t LEFT JOIN category c ON t.category_id = c.id " +
                  "WHERE t.app_user_id = :appUserId AND t.date BETWEEN :startDate AND :endDate",
          countQuery = "SELECT COUNT(*) FROM transaction t WHERE t.app_user_id = :appUserId AND t.date BETWEEN :startDate AND :endDate",
          nativeQuery = true)
  Page<Transaction> findAllByUserAndDateBetween(@Param("appUserId") Long appUserId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate,
                                                Pageable pageable);

  @Query(
          value = "SELECT * FROM transaction t WHERE t.app_user_id = :appUserId",
          countQuery = "SELECT COUNT(*) FROM transaction t WHERE t.app_user_id = :appUserId",
          nativeQuery = true)
  Page<Transaction> findAllByUser(@Param("appUserId") Long appUserId, Pageable pageable);

  @Query(value =
          "SELECT * \n" +
          "FROM transaction t\n" +
          "LEFT JOIN category c\n" +
          "ON t.category_id = c.id\n" +
          "WHERE t.app_user_id = :appUserId " +
                  "AND t.category_id = :categoryId " +
                  "AND t.date BETWEEN :startDate " +
                  "AND :endDate",
          nativeQuery = true)
  List<Transaction> getTransactionsByCategoryAndDateBetween(@Param("appUserId") Long appUserId,
                                                            @Param("categoryId") Long categoryId,
                                                            @Param("startDate") LocalDate startDate,
                                                            @Param("endDate") LocalDate endDate);

  // TODO: handle when no dates are provided
  @Query(value =
          "SELECT SUM(t.amount)\n" +
          "FROM transaction t\n" +
          "LEFT JOIN category c ON t.category_id = c.id\n" +
          "WHERE t.app_user_id = :appUserId AND t.date >= :startDate AND t.date <= :endDate AND c.type = :categoryType", nativeQuery = true)
  Optional<Double> getSumOfAmountByCategoryType(@Param("appUserId") Long appUserId,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate,
                                                @Param("categoryType") String categoryType);
}
