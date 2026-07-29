package com.finsight.backend.service;

import com.finsight.backend.dto.ExpenseRequest;
import com.finsight.backend.entity.Expense;
import com.finsight.backend.entity.User;
import com.finsight.backend.repository.ExpenseRepository;
import com.finsight.backend.repository.UserRepository;
import com.finsight.backend.dto.ExpenseSummaryResponse;
import com.finsight.backend.dto.CategoryExpenseResponse;
import com.finsight.backend.dto.MonthlyExpenseResponse;
import com.finsight.backend.dto.ExpenseResponse;
import com.finsight.backend.exception.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

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
                        new ResourceNotFoundException("User not found"));

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
     
    public List<ExpenseResponse> getMyExpenses(
        Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    return expenseRepository.findByUser(user)
            .stream()
            .map(this::mapToResponse)
            .toList();
    }
     
    public Page<ExpenseResponse> getMyExpenses(
        Authentication authentication,
        int page,
        int size,
        String sortBy,
        String direction) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Sort sort = direction.equalsIgnoreCase("desc")
            ? Sort.by(sortBy).descending()
            : Sort.by(sortBy).ascending();

    Pageable pageable = PageRequest.of(page, size, sort);

    Page<Expense> expensePage =
            expenseRepository.findByUser(user, pageable);

    return expensePage.map(this::mapToResponse);
    }
   
    public String updateExpense(Long expenseId,
                            ExpenseRequest request,
                            Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    Expense expense = expenseRepository
            .findByIdAndUser(expenseId, user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Expense not found"));

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
                    new ResourceNotFoundException("User not found"));

    Expense expense = expenseRepository
            .findByIdAndUser(expenseId, user)
            .orElseThrow(() ->
                    new ResourceNotFoundException("Expense not found"));

    expenseRepository.delete(expense);

    return "Expense deleted successfully";
    }
    public ExpenseSummaryResponse getExpenseSummary(
        Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

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
                    new ResourceNotFoundException("User not found"));

    return expenseRepository.getCategoryWiseExpenses(user);
    }

    public List<MonthlyExpenseResponse> getMonthlyExpenses(
        Authentication authentication) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    List<Object[]> results = expenseRepository.getMonthlyExpenses(user);

    List<MonthlyExpenseResponse> response = new ArrayList<>();

    for (Object[] row : results) {

        String month = (String) row[0];
        Double totalAmount = ((Number) row[1]).doubleValue();

        response.add(new MonthlyExpenseResponse(month, totalAmount));
    }

    return response;
    }
    public List<ExpenseResponse> getExpensesByDateRange(
        Authentication authentication,
        LocalDate startDate,
        LocalDate endDate) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    return expenseRepository.findByUserAndDateBetween(
            user,
            startDate,
            endDate
    )
    .stream()
    .map(this::mapToResponse)
    .toList();
    }

    public List<ExpenseResponse> searchExpenses(
        Authentication authentication,
        String keyword) {

    String email = authentication.getName();

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new ResourceNotFoundException("User not found"));

    return expenseRepository
            .findByUserAndTitleContainingIgnoreCaseOrUserAndCategoryContainingIgnoreCase(
                    user,
                    keyword,
                    user,
                    keyword
            )
            .stream()
            .map(this::mapToResponse)
            .toList();
    }

    private ExpenseResponse mapToResponse(Expense expense) {

    return new ExpenseResponse(
            expense.getId(),
            expense.getTitle(),
            expense.getAmount(),
            expense.getCategory(),
            expense.getDate(),
            expense.getDescription()
    );
    } 
}