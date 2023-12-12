package com.example.banking.dto

import java.math.BigDecimal

data class AccountResponse (
    val id: Long,
    val beneficiaryName: String,
    val balance: BigDecimal
)