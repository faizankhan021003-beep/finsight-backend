package com.finsight.backend.service;

import com.finsight.backend.dto.ExpenseRequest;
import com.finsight.backend.entity.Expense;
import com.finsight.backend.entity.User;
import com.finsight.backend.repository.ExpenseRepository;
import com.finsight.backend.repository.UserRepository;
import com.finsight.backend.dto.ExpenseSummaryResponse;
import com.finsight.backend.dto.CategoryExpenseResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    public ExpenseService(ExpenseRepository expenseRepository,
                          UserRepository userRepository) {

        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public String addExpense(ExpenseRequest request,
                             Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Expense expense = new Expense();

        expense.setTitle(request.getTitle());
        expense.setAmount(request.getAmount());
        expense.setCategory(request.getCategory());
        expense.setDate(request.getDate());
        expense.setDescription(request.getDescription());

        expense.setUser(user);

        expenseRepository.save(expense);

        return "Expense added successfully";
    }

    public List<Expense> getMyExpenses(
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return expenseRepository.findByUser(user);
    }
    public String updateExpense(Long expenseId,
                            ExpenseRequest request,
                            Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    Expense expense = expenseRepository
            .findByIdAndUser(expenseId, user)
            .orElseThrow(() ->
                    new RuntimeException("Expense not found"));

    expense.setTitle(request.getTitle());
    expense.setAmount(request.getAmount());
    expense.setCategory(request.getCategory());
    expense.setDate(request.getDate());
    expense.setDescription(request.getDescription());

    expenseRepository.save(expense);

    return "Expense updated successfully";
    }
    public String deleteExpense(Long expenseId,
                            Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    Expense expense = expenseRepository
            .findByIdAndUser(expenseId, user)
            .orElseThrow(() ->
                    new RuntimeException("Expense not found"));

    expenseRepository.delete(expense);

    return "Expense deleted successfully";
    }
    public ExpenseSummaryResponse getExpenseSummary(
        Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    Double totalExpenses = expenseRepository.sumAmountByUser(user);

    Long totalTransactions = expenseRepository.countByUser(user);

    if (totalExpenses == null) {
        totalExpenses = 0.0;
    }

    return new ExpenseSummaryResponse(
            totalExpenses,
            totalTransactions
    );
    }
    public List<CategoryExpenseResponse> getCategoryWiseExpenses(
        Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new RuntimeException("User not found"));

    return expenseRepository.getCategoryWiseExpenses(user);
    }
}