package com.cheong.agenticai.dto;

public record PaymentResult(
        boolean success,
        String transactionId,
        String message,
        String timestamp
) {}
