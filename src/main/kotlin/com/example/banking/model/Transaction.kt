package com.example.banking.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    @Column(nullable = false)
    var accountId: Long,
    @Column(nullable = false)
    var transactionType: TransactionType,
    @Column(nullable = false)
    var amount: BigDecimal,
    @Column(nullable = false)
    var timestamp: LocalDateTime
)

enum class TransactionType {
    DEPOSIT, WITHDRAWAL, TRANSFER
}