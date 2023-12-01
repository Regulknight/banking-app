package com.example.banking.service

import com.example.banking.dto.AccountRequest
import com.example.banking.dto.TransactionRequest
import com.example.banking.exception.InsufficientFundsException
import com.example.banking.model.TransactionType
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import kotlin.concurrent.thread
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@SpringBootTest
@Transactional
@DirtiesContext
@ActiveProfiles("test")
class TransactionServiceTest {

    @Autowired
    lateinit var transactionService: TransactionService

    @Autowired
    lateinit var accountService: AccountService

    @Test
    fun testDeposit() {
        val initialBalance = BigDecimal(100.0)
        val accountId = createTestAccount(initialBalance)

        val amountToDeposit = BigDecimal(50.0)
        val pin = "1234"

        val transaction = transactionService.deposit(accountId, TransactionRequest(amountToDeposit, pin))

        val updatedAccount = accountService.getAccountById(accountId)

        assertEquals(initialBalance + amountToDeposit, updatedAccount.balance)
        assertEquals(TransactionType.DEPOSIT, transaction.transactionType)
        assertEquals(amountToDeposit, transaction.amount)
    }

    @Test
    fun testWithdraw() {
        val initialBalance = BigDecimal(100.0)
        val accountId = createTestAccount(initialBalance)

        val amountToWithdraw = BigDecimal(30.0)
        val pin = "1234"

        val transaction = transactionService.withdraw(accountId, TransactionRequest(amountToWithdraw, pin))

        val updatedAccount = accountService.getAccountById(accountId)

        assertEquals(initialBalance - amountToWithdraw, updatedAccount.balance)
        assertEquals(TransactionType.WITHDRAWAL, transaction.transactionType)
        assertEquals(amountToWithdraw, transaction.amount)
    }

    @Test
    fun testWithdrawInsufficientFunds() {
        val initialBalance = BigDecimal(20.0)
        val accountId = createTestAccount(initialBalance)

        val amountToWithdraw = BigDecimal(30.0)
        val pin = "1234"

        assertFailsWith<InsufficientFundsException> {
            transactionService.withdraw(accountId, TransactionRequest(amountToWithdraw, pin))
        }
    }

    @Test
    fun testTransfer() {
        val sourceAccountBalance = BigDecimal(100.0)
        val targetAccountBalance = BigDecimal(50.0)
        val sourceAccountId = createTestAccount(sourceAccountBalance)
        val targetAccountId = createTestAccount(targetAccountBalance)

        val amountToTransfer = BigDecimal(30.0)
        val pin = "1234"

        val transaction = transactionService.transfer(
            sourceAccountId,
            targetAccountId,
            TransactionRequest(amountToTransfer, pin)
        )

        val updatedSourceAccount = accountService.getAccountById(sourceAccountId)
        val updatedTargetAccount = accountService.getAccountById(targetAccountId)

        assertEquals(sourceAccountBalance - amountToTransfer, updatedSourceAccount.balance)
        assertEquals(targetAccountBalance + amountToTransfer, updatedTargetAccount.balance)
        assertEquals(TransactionType.TRANSFER, transaction.transactionType)
        assertEquals(amountToTransfer, transaction.amount)
    }

    @Test
    fun testTransferInsufficientFunds() {
        val sourceAccountBalance = BigDecimal(20.0)
        val targetAccountBalance = BigDecimal(50.0)
        val sourceAccountId = createTestAccount(sourceAccountBalance)
        val targetAccountId = createTestAccount(targetAccountBalance)

        val amountToTransfer = BigDecimal(30.0)
        val pin = "1234"

        assertFailsWith<InsufficientFundsException> {
            transactionService.transfer(sourceAccountId, targetAccountId, TransactionRequest(amountToTransfer, pin))
        }
    }

    @Test
    fun testConcurrentTransfer() {
        val sourceAccountBalance = BigDecimal(100.0)
        val targetAccountBalance = BigDecimal(50.0)
        val sourceAccountId = createTestAccount(sourceAccountBalance)
        val targetAccountId = createTestAccount(targetAccountBalance)

        val amountToTransfer = BigDecimal(30.0)
        val pin = "1234"

        val thread1 = thread {
            transactionService.transfer(sourceAccountId, targetAccountId, TransactionRequest(amountToTransfer, pin))
        }

        val thread2 = thread {
            transactionService.transfer(sourceAccountId, targetAccountId, TransactionRequest(amountToTransfer, pin))
        }

        thread1.join()
        thread2.join()

        val updatedSourceAccount = accountService.getAccountById(sourceAccountId)
        val updatedTargetAccount = accountService.getAccountById(targetAccountId)

        assertEquals(sourceAccountBalance - amountToTransfer, updatedSourceAccount.balance)
        assertEquals(targetAccountBalance + amountToTransfer, updatedTargetAccount.balance)
    }

    private fun createTestAccount(initialBalance: BigDecimal): Long {
        val accountRequest = AccountRequest("Test Beneficiary", "1234")
        val account = accountService.createAccount(accountRequest)
        account.balance = initialBalance
        return account.id!!
    }
}
