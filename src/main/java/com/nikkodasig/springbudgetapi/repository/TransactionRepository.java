package com.nikkodasig.springbudgetapi.repository;

import com.nikkodasig.springbudgetapi.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
