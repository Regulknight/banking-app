package com.example.banking.service

import com.example.banking.dto.TransactionRequest
import com.example.banking.exception.InsufficientFundsException
import com.example.banking.exception.InvalidPinException
import com.example.banking.model.Account
import com.example.banking.model.Transaction
import com.example.banking.model.TransactionType
import com.example.banking.repository.TransactionRepository
import com.example.banking.utils.SHA256Hashing
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository,
    private val accountService: AccountService,
    private val shA256Hashing: SHA256Hashing
) {

    @Synchronized
    fun deposit(accountId: Long, transactionRequest: TransactionRequest): Transaction {
        val account = accountService.getAccountById(accountId)
        shA256Hashing.validatePin(account, transactionRequest.pin)

        val amount = transactionRequest.amount
        account.balance = account.balance + amount
        accountService.updateAccount(account)

        val transaction = saveTransaction(accountId, TransactionType.DEPOSIT, amount)
        return transaction
    }

    @Synchronized
    fun withdraw(accountId: Long, transactionRequest: TransactionRequest): Transaction {
        val account = accountService.getAccountById(accountId)
        shA256Hashing.validatePin(account, transactionRequest.pin)

        val amount = transactionRequest.amount
        if (account.balance < amount) {
            throw InsufficientFundsException("Insufficient funds")
        }

        account.balance -= amount
        accountService.updateAccount(account)

        val transaction = saveTransaction(accountId, TransactionType.WITHDRAWAL, amount)
        return transaction
    }

    @Synchronized
    fun transfer(sourceAccountId: Long, targetAccountId: Long, transactionRequest: TransactionRequest): Transaction {
        val sourceAccount = accountService.getAccountById(sourceAccountId)
        shA256Hashing.validatePin(sourceAccount, transactionRequest.pin)

        val targetAccount = accountService.getAccountById(targetAccountId)

        val amount = transactionRequest.amount
        if (sourceAccount.balance < amount) {
            throw InsufficientFundsException("Insufficient funds")
        }

        sourceAccount.balance -= amount
        targetAccount.balance += amount

        accountService.updateAccount(sourceAccount)
        accountService.updateAccount(targetAccount)

        val transaction = saveTransaction(sourceAccountId, TransactionType.TRANSFER, -amount)
        saveTransaction(targetAccountId, TransactionType.TRANSFER, amount)

        return transaction
    }

    fun getAccountTransactions(accountId: Long): List<Transaction> {
        return transactionRepository.findByAccountId(accountId)
    }

    private fun saveTransaction(accountId: Long, type: TransactionType, amount: BigDecimal): Transaction {
        val transaction = Transaction(
            accountId = accountId,
            transactionType = type,
            amount = amount,
            timestamp = LocalDateTime.now()
        )
        return transactionRepository.save(transaction)
    }


}
