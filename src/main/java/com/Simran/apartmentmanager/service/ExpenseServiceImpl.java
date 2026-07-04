package com.Simran.apartmentmanager.service;

import com.Simran.apartmentmanager.dto.ExpenseCategoryRequest;
import com.Simran.apartmentmanager.dto.ExpenseCategoryResponse;
import com.Simran.apartmentmanager.dto.ExpenseRequest;
import com.Simran.apartmentmanager.dto.ExpenseResponse;
import com.Simran.apartmentmanager.entity.Expense;
import com.Simran.apartmentmanager.entity.ExpenseCategory;
import com.Simran.apartmentmanager.entity.User;
import com.Simran.apartmentmanager.exception.BadRequestException;
import com.Simran.apartmentmanager.exception.ResourceNotFoundException;
import com.Simran.apartmentmanager.repository.ExpenseCategoryRepository;
import com.Simran.apartmentmanager.repository.ExpenseRepository;
import com.Simran.apartmentmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    ExpenseCategoryRepository expenseCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ExpenseCategoryResponse addCategory(ExpenseCategoryRequest request){
        //1. check if category already exists
        if(expenseCategoryRepository.existsByName(request.getName()))
        {
            throw new BadRequestException("Category already exists: " + request.getName());
        }

        //2. Create category
        ExpenseCategory expenseCategory=new ExpenseCategory();
        expenseCategory.setName(request.getName());
        expenseCategory.setType(request.getType());
        expenseCategory.setFrequency(request.getFrequency());
        expenseCategory.setCreatedAt(LocalDateTime.now());

        //3. save category
        ExpenseCategory savedCategory = expenseCategoryRepository.save(expenseCategory);

        //4. Create Expense Response
        ExpenseCategoryResponse response=new ExpenseCategoryResponse();
        response.setId(savedCategory.getId());
        response.setType(savedCategory.getType());
        response.setFrequency(savedCategory.getFrequency());
        response.setName(savedCategory.getName());
        response.setCreatedAt(savedCategory.getCreatedAt());

        return response;

    }

    @Override
    public List<ExpenseCategoryResponse> getAllCategories(){
        List<ExpenseCategory> categories = expenseCategoryRepository.findAll();

        ArrayList<ExpenseCategoryResponse>responseList=new ArrayList<>();

        for(ExpenseCategory category: categories)
        {
            ExpenseCategoryResponse response=new ExpenseCategoryResponse();
            response.setId(category.getId());
            response.setName(category.getName());
            response.setType(category.getType());
            response.setFrequency(category.getFrequency());
            response.setCreatedAt(category.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }

    @Override
    public ExpenseResponse addExpense(ExpenseRequest request){
        // Step 1 - Get logged in Pradhana
        String email = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User pradhana = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        // Step 2 - Find category
        ExpenseCategory category = expenseCategoryRepository
                .findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()));

        // Step 3 - Create expense
        Expense expense = new Expense();
        expense.setCategory(category);
        expense.setAddedBy(pradhana);
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setExpenseDate(request.getExpenseDate());
        expense.setReceiptUrl(request.getReceiptUrl());
        expense.setCreatedAt(LocalDateTime.now());

        // Step 4 - Save expense
        Expense savedExpense = expenseRepository.save(expense);

        ExpenseResponse response = new ExpenseResponse();
        response.setId(savedExpense.getId());
        response.setCategoryId(savedExpense.getCategory().getId());
        response.setCategoryName(savedExpense.getCategory().getName());
        response.setCategoryType(savedExpense.getCategory().getType());
        response.setAddedByName(savedExpense.getAddedBy().getName());
        response.setAmount(savedExpense.getAmount());
        response.setDescription(savedExpense.getDescription());
        response.setExpenseDate(savedExpense.getExpenseDate());
        response.setReceiptUrl(savedExpense.getReceiptUrl());
        response.setCreatedAt(savedExpense.getCreatedAt());

        return response;

    }

    @Override
    public List<ExpenseResponse> getAllExpenses(){
        List<ExpenseResponse> responseList = new ArrayList<>();
        List<Expense> expenses = expenseRepository.findAll();

        for (Expense expense : expenses) {
            ExpenseResponse response = new ExpenseResponse();
            response.setId(expense.getId());
            response.setCategoryId(expense.getCategory().getId());
            response.setCategoryName(expense.getCategory().getName());
            response.setCategoryType(expense.getCategory().getType());
            response.setAddedByName(expense.getAddedBy().getName());
            response.setAmount(expense.getAmount());
            response.setDescription(expense.getDescription());
            response.setExpenseDate(expense.getExpenseDate());
            response.setReceiptUrl(expense.getReceiptUrl());
            response.setCreatedAt(expense.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }


    @Override
    public List<ExpenseResponse> getExpensesByMonthAndYear(int month, int year) {

        List<Expense> expenses = expenseRepository
                .findByMonthAndYear(month, year);//find expenses by month and year
        List<ExpenseResponse> responseList = new ArrayList<>();

        for (Expense expense : expenses) {
            ExpenseResponse response = new ExpenseResponse();
            response.setId(expense.getId());
            response.setCategoryId(expense.getCategory().getId());
            response.setCategoryName(expense.getCategory().getName());
            response.setCategoryType(expense.getCategory().getType());
            response.setAddedByName(expense.getAddedBy().getName());
            response.setAmount(expense.getAmount());
            response.setDescription(expense.getDescription());
            response.setExpenseDate(expense.getExpenseDate());
            response.setReceiptUrl(expense.getReceiptUrl());
            response.setCreatedAt(expense.getCreatedAt());
            responseList.add(response);
        }

        return responseList;
    }
}
