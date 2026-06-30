package com.finsight.backend.service;

import com.finsight.backend.dto.ExpenseRequest;
import com.finsight.backend.entity.Expense;
import com.finsight.backend.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public String addExpense(ExpenseRequest request) {

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());

        // Automatically save today's date
        expense.setDate(LocalDate.now());

        expenseRepository.save(expense);

        return "Expense added successfully";
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
}
