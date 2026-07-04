package com.Simran.apartmentmanager.controller;

import com.Simran.apartmentmanager.dto.ExpenseCategoryRequest;
import com.Simran.apartmentmanager.dto.ExpenseCategoryResponse;
import com.Simran.apartmentmanager.dto.ExpenseRequest;
import com.Simran.apartmentmanager.dto.ExpenseResponse;
import com.Simran.apartmentmanager.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    ExpenseService expenseService;

    // Add category - Pradhana only
    @PostMapping("/category/add")
    public ResponseEntity<ExpenseCategoryResponse>addCategory(@Valid @RequestBody ExpenseCategoryRequest request) {

        return new ResponseEntity<>(expenseService.addCategory(request), HttpStatus.CREATED);
    }

    // Get all categories
    @GetMapping("/category/all")
    public ResponseEntity<List<ExpenseCategoryResponse>> getAllCategories() {

        return new ResponseEntity<>(expenseService.getAllCategories(), HttpStatus.OK);
    }

    // Add expense - Pradhana only
    @PostMapping("/add")
    public ResponseEntity<ExpenseResponse> addExpense(@Valid @RequestBody ExpenseRequest request) {

        return new ResponseEntity<>(expenseService.addExpense(request), HttpStatus.CREATED);
    }

    // Get all expenses
    @GetMapping("/all")
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses() {

        return new ResponseEntity<>(expenseService.getAllExpenses(), HttpStatus.OK);
    }

    @GetMapping("/month/{month}/year/{year}")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByMonthAndYear(@PathVariable int month, @PathVariable int year) {

        return new ResponseEntity<>(expenseService.getExpensesByMonthAndYear(month, year), HttpStatus.OK);
    }
}
