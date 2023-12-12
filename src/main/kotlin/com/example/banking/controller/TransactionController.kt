package com.example.banking.controller

import com.example.banking.dto.TransactionRequest
import com.example.banking.dto.TransactionResponse
import com.example.banking.model.Transaction
import com.example.banking.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/transaction")
class TransactionController(
    private val transactionService: TransactionService
) {
    @PostMapping("/{accountId}/deposit")
    fun deposit(
        @PathVariable accountId: Long,
        @RequestBody transactionRequest: TransactionRequest
    ): ResponseEntity<TransactionResponse> {
        transactionService.deposit(accountId, transactionRequest)
        return ResponseEntity(TransactionResponse("Transaction processed successfully"), HttpStatus.OK)
    }

    @PostMapping("/{accountId}/withdraw")
    fun withdraw(
        @PathVariable accountId: Long,
        @RequestBody transactionRequest: TransactionRequest
    ): ResponseEntity<TransactionResponse> {
        transactionService.withdraw(accountId, transactionRequest)
        return ResponseEntity(TransactionResponse("Transaction processed successfully"), HttpStatus.OK)
    }

    @PostMapping("/{sourceAccountId}/transfer/{targetAccountId}")
    fun transfer(
        @PathVariable sourceAccountId: Long,
        @PathVariable targetAccountId: Long,
        @RequestBody transactionRequest: TransactionRequest
    ): ResponseEntity<TransactionResponse> {
        transactionService.transfer(sourceAccountId, targetAccountId, transactionRequest)
        return ResponseEntity(TransactionResponse("Transaction processed successfully"), HttpStatus.OK)
    }

    @GetMapping("/{accountId}")
    fun getAccountTransactions(
        @PathVariable accountId: Long
    ): ResponseEntity<List<Transaction>> {
        val transactionList = transactionService.getAccountTransactions(accountId)
        return ResponseEntity(transactionList, HttpStatus.OK)
    }
}