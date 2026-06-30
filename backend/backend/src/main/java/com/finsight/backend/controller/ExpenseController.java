package com.finsight.backend.controller;

import com.finsight.backend.dto.ExpenseRequest;
import com.finsight.backend.entity.Expense;
import com.finsight.backend.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public String addExpense(@RequestBody ExpenseRequest request) {
        return expenseService.addExpense(request);
    }

    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }
}