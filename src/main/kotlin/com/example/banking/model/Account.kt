package com.example.banking.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long? = null,
    @Column(nullable = false)
    var beneficiaryName: String,
    @Column(nullable = false)
    var pin: String,
    @Column(nullable = false)
    var balance: BigDecimal
)

