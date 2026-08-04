package com.finsight.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SuccessResponse {

    private boolean success;
    private String message;
    private LocalDateTime timestamp;
}