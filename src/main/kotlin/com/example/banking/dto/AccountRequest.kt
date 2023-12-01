package com.example.banking.dto

data class AccountRequest(
    val beneficiaryName: String,
    val pin: String
)