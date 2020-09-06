package com.nikkodasig.springbudgetapi.repository;

import com.nikkodasig.springbudgetapi.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
