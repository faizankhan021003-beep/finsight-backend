package com.finsight.backend.repository;

import com.finsight.backend.entity.Expense;
import com.finsight.backend.entity.User;
import com.finsight.backend.dto.CategoryExpenseResponse;
import com.finsight.backend.dto.MonthlyExpenseResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Get all expenses of a user
    List<Expense> findByUser(User user);

    Page<Expense> findByUser(User user, Pageable pageable);

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

    @Query(value = """
    SELECT
    DATE_FORMAT(date,'%Y-%m') AS month,
    SUM(amount) AS totalAmount
    FROM expenses
    WHERE user_id = :#{#user.id}
    GROUP BY DATE_FORMAT(date,'%Y-%m')
    ORDER BY DATE_FORMAT(date,'%Y-%m')
    """, nativeQuery = true)
    List<Object[]> getMonthlyExpenses(@Param("user") User user);
    
    List<Expense> findByUserAndDateBetween(
        User user,
        LocalDate startDate,
        LocalDate endDate);

    List<Expense> findByUserAndTitleContainingIgnoreCaseOrUserAndCategoryContainingIgnoreCase(
    User user,
    String title,
    User userAgain,
    String category);

    @Query("SELECT MAX(e.amount) FROM Expense e WHERE e.user = :user")
    Double getHighestExpense(@Param("user") User user);

    @Query("SELECT MIN(e.amount) FROM Expense e WHERE e.user = :user")
    Double getLowestExpense(@Param("user") User user);

    @Query("SELECT AVG(e.amount) FROM Expense e WHERE e.user = :user")
    Double getAverageExpense(@Param("user") User user);

}