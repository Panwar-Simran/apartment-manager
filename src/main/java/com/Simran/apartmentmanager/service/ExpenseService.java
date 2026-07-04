package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.ExpenseCategoryRequest;
import com.Simran.apartmentmanager.dto.ExpenseCategoryResponse;
import com.Simran.apartmentmanager.dto.ExpenseRequest;
import com.Simran.apartmentmanager.dto.ExpenseResponse;

import java.util.List;

public interface ExpenseService {
    // add category in expense category
    ExpenseCategoryResponse addCategory(ExpenseCategoryRequest request);

     //return list of types of expenses
    List<ExpenseCategoryResponse> getAllCategories();

    //add expense
    ExpenseResponse addExpense(ExpenseRequest request);

    //returns list of expenses
    List<ExpenseResponse> getAllExpenses();

    //return list of responses for a particular month of a year
    List<ExpenseResponse> getExpensesByMonthAndYear(int month, int year);
}
