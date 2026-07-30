package com.finsight.backend.controller;

import com.finsight.backend.dto.ExpenseRequest;
import com.finsight.backend.dto.ExpenseResponse;
import com.finsight.backend.entity.Expense;
import com.finsight.backend.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.finsight.backend.dto.ExpenseSummaryResponse;
import com.finsight.backend.dto.CategoryExpenseResponse;
import com.finsight.backend.dto.MonthlyExpenseResponse;
import com.finsight.backend.dto.ExpenseStatisticsResponse;

import java.util.List;
import java.time.LocalDate;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    // Add a new expense
    @PostMapping
    public String addExpense(
        @Valid @RequestBody ExpenseRequest request,
        Authentication authentication) {

    return expenseService.addExpense(request, authentication);
    }

    // Get all expenses of the logged-in user
    @GetMapping
    public List<ExpenseResponse> getMyExpenses(
        Authentication authentication) {

    return expenseService.getMyExpenses(authentication);
    }

    @GetMapping("/page")
    public Page<ExpenseResponse> getMyExpensesWithPagination(
        Authentication authentication,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size,
        @RequestParam(defaultValue = "date") String sortBy,
        @RequestParam(defaultValue = "desc") String direction) {

    return expenseService.getMyExpenses(
            authentication,
            page,
            size,
            sortBy,
            direction
    );
    }
    // Get expense summary
    @GetMapping("/summary")
    public ExpenseSummaryResponse getExpenseSummary(
        Authentication authentication) {

    return expenseService.getExpenseSummary(authentication);
    }
    @GetMapping("/statistics")
    public ExpenseStatisticsResponse getExpenseStatistics(
        Authentication authentication) {

    return expenseService.getExpenseStatistics(authentication);
    }
    // Get category-wise expense summary
    @GetMapping("/category-summary")
    public List<CategoryExpenseResponse> getCategoryWiseExpenses(
        Authentication authentication) {

    return expenseService.getCategoryWiseExpenses(authentication);
    }
    // Update an existing expense
    @PutMapping("/{id}")
    public String updateExpense(@PathVariable Long id,
                            @Valid @RequestBody ExpenseRequest request,
                            Authentication authentication) {

    return expenseService.updateExpense(id, request, authentication);
    }
    // Delete an expense
    @DeleteMapping("/{id}")
    public String deleteExpense(@PathVariable Long id,
                            Authentication authentication) {

    return expenseService.deleteExpense(id, authentication);
    }

    // Get monthly expense summary
    @GetMapping("/monthly-summary")
    public List<MonthlyExpenseResponse> getMonthlyExpenses(
        Authentication authentication) {

    return expenseService.getMonthlyExpenses(authentication);
    }

    @GetMapping("/filter")
    public List<ExpenseResponse> getExpensesByDateRange(
        Authentication authentication,
        @RequestParam LocalDate startDate,
        @RequestParam LocalDate endDate) {

    return expenseService.getExpensesByDateRange(
            authentication,
            startDate,
            endDate
    );
    }

    @GetMapping("/search")
    public List<ExpenseResponse> searchExpenses(
        Authentication authentication,
        @RequestParam String keyword) {

    return expenseService.searchExpenses(authentication, keyword);
    }
}