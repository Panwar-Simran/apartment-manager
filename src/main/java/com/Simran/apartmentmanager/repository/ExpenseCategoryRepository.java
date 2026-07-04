package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.Expense;
import com.Simran.apartmentmanager.entity.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    Boolean existsByName(String name);
}
