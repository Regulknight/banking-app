package com.example.banking.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,
    @Column(nullable = false)
    val beneficiaryName: String,
    @Column(nullable = false)
    val pin: String,
    @Column(nullable = false)
    var balance: BigDecimal
)

