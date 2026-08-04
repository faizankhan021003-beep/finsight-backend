package com.finsight.backend.controller;

import com.finsight.backend.dto.ExpenseRequest;
import com.finsight.backend.dto.ExpenseResponse;
import com.finsight.backend.service.ExpenseService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import com.finsight.backend.dto.ExpenseSummaryResponse;
import com.finsight.backend.dto.CategoryExpenseResponse;
import com.finsight.backend.dto.MonthlyExpenseResponse;
import com.finsight.backend.dto.ExpenseStatisticsResponse;
import com.finsight.backend.dto.SuccessResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


import java.util.List;
import java.time.LocalDate;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/expenses")
@Tag(name = "Expense APIs", description = "Manage user expenses")
@SecurityRequirement(name = "Bearer Authentication")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    
    @Operation(
    summary = "Add a new expense",
    description = "Creates a new expense for the authenticated user."
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Expense added successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid request"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    // Add a new expense
    @PostMapping
    public SuccessResponse addExpense(
        @Valid @RequestBody ExpenseRequest request,
        Authentication authentication) {

    return expenseService.addExpense(request, authentication);
    }
    @Operation(
    summary = "Get all expenses",
    description = "Returns all expenses of the authenticated user."
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
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
    @Operation(
    summary = "Get expense summary",
    description = "Returns total expenses and total transactions."
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Summary retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    // Get expense summary
    @GetMapping("/summary")
    public ExpenseSummaryResponse getExpenseSummary(
        Authentication authentication) {

    return expenseService.getExpenseSummary(authentication);
    }
    @Operation(
    summary = "Get expense statistics",
    description = "Returns highest, lowest, average expense and total transactions."
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
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
    @Operation(
    summary = "Update an expense",
    description = "Updates an existing expense by its ID."
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
    @ApiResponse(responseCode = "404", description = "Expense not found"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    // Update an existing expense
    @PutMapping("/{id}")
    public SuccessResponse updateExpense(@PathVariable Long id,
                            @Valid @RequestBody ExpenseRequest request,
                            Authentication authentication) {

    return expenseService.updateExpense(id, request, authentication);
    }
    @Operation(
    summary = "Delete an expense",
    description = "Deletes an expense by its ID."
    )
    @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Expense deleted successfully"),
    @ApiResponse(responseCode = "404", description = "Expense not found"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    // Delete an expense
    @DeleteMapping("/{id}")
    public SuccessResponse deleteExpense(@PathVariable Long id,
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