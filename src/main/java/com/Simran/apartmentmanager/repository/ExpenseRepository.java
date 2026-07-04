package com.Simran.apartmentmanager.repository;

import com.Simran.apartmentmanager.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    //Find expenses by month and year

    @Query("SELECT e FROM Expense e WHERE MONTH(e.expenseDate) = :month AND YEAR(e.expenseDate) = :year")
    List<Expense> findByMonthAndYear(@Param("month") int month,
                                     @Param("year") int year);
}
