package com.finsight.backend.repository;

import com.finsight.backend.entity.Expense;
import com.finsight.backend.entity.User;
import com.finsight.backend.dto.CategoryExpenseResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Get all expenses of a user
    List<Expense> findByUser(User user);

    // Get a specific expense belonging to a user
    Optional<Expense> findByIdAndUser(Long id, User user);

    // Calculate total amount spent by a user
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user = :user")
    Double sumAmountByUser(@Param("user") User user);

    // Count total expenses of a user
    Long countByUser(User user);

    @Query("""
    SELECT new com.finsight.backend.dto.CategoryExpenseResponse(
        e.category,
        SUM(e.amount)
    )
    FROM Expense e
    WHERE e.user = :user
    GROUP BY e.category
    """)
    List<CategoryExpenseResponse> getCategoryWiseExpenses(
        @Param("user") User user);
}