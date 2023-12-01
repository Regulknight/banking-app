package com.example.banking.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
    @Column(nullable = false)
    val accountId: Long,
    @Column(nullable = false)
    val transactionType: TransactionType,
    @Column(nullable = false)
    val amount: BigDecimal,
    @Column(nullable = false)
    val timestamp: LocalDateTime
)

enum class TransactionType {
    DEPOSIT, WITHDRAWAL, TRANSFER
}