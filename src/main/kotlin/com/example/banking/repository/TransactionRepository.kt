package com.example.banking.repository

import com.example.banking.model.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {
    fun findByAccountId(accountId: Long): List<Transaction>
}