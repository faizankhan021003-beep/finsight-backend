package com.finsight.backend.controller;

import com.finsight.backend.dto.ExpenseRequest;
import com.finsight.backend.entity.Expense;
import com.finsight.backend.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Add a new expense
    @PostMapping
    public String addExpense(@RequestBody ExpenseRequest request,
                             Authentication authentication) {

        return expenseService.addExpense(request, authentication);
    }

    // Get all expenses of the logged-in user
    @GetMapping
    public List<Expense> getMyExpenses(Authentication authentication) {

        return expenseService.getMyExpenses(authentication);
    }
    // Update an existing expense
    @PutMapping("/{id}")
    public String updateExpense(@PathVariable Long id,
                            @RequestBody ExpenseRequest request,
                            Authentication authentication) {

    return expenseService.updateExpense(id, request, authentication);
    }
    // Delete an expense
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id,
                            Authentication authentication) {

    return expenseService.deleteExpense(id, authentication);
    }
}