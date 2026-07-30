package com.finsight.backend.dto;

public class ExpenseStatisticsResponse {

    private Double highestExpense;
    private Double lowestExpense;
    private Double averageExpense;
    private Long totalTransactions;

    public ExpenseStatisticsResponse(
            Double highestExpense,
            Double lowestExpense,
            Double averageExpense,
            Long totalTransactions) {

        this.highestExpense = highestExpense;
        this.lowestExpense = lowestExpense;
        this.averageExpense = averageExpense;
        this.totalTransactions = totalTransactions;
    }

    public Double getHighestExpense() {
        return highestExpense;
    }

    public Double getLowestExpense() {
        return lowestExpense;
    }

    public Double getAverageExpense() {
        return averageExpense;
    }

    public Long getTotalTransactions() {
        return totalTransactions;
    }
}
