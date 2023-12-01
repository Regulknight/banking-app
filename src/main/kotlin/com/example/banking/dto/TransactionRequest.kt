package com.example.banking.dto

import java.math.BigDecimal

data class TransactionRequest(
    val amount: BigDecimal,
    val pin: String
)