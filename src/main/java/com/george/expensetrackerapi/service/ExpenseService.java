package com.george.expensetrackerapi.service;

import com.george.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExpenseService {

    Page<Expense> getAllExpenses(Pageable page);

    Expense getExpenseById(Long id);

    void deleteExpenseById(Long id);

    Expense saveExpenseDetails(Expense expense);

    Expense updateExpenseDetails( Long id, Expense expense);

    List<Expense> getExpenseByCategory(String category, Pageable page);

    List<Expense> getExpenseByName(String keyword, Pageable page);
}
