package com.finsight.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseRequest {

    private String title;

    private Double amount;

    private String category;
}
