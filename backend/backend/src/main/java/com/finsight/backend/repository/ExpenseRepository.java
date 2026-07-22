package com.finsight.backend.repository;

import com.finsight.backend.entity.Expense;
import com.finsight.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    // Get all expenses of a user
    List<Expense> findByUser(User user);

    // Get a specific expense belonging to a user
    Optional<Expense> findByIdAndUser(Long id, User user);
}