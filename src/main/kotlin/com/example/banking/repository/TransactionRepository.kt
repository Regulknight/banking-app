package com.example.banking.repository

import com.example.banking.model.Transaction
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : CrudRepository<Transaction, Long> {
    fun findByAccountId(accountId: Long): List<Transaction>
}