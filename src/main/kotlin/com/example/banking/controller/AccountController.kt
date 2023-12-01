package com.example.banking.controller

import com.example.banking.dto.AccountRequest
import com.example.banking.dto.TransactionRequest
import com.example.banking.service.AccountService
import com.example.banking.model.Account
import com.example.banking.model.Transaction
import com.example.banking.service.TransactionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/accounts")
class AccountController(
    private val accountService: AccountService
) {

    @PostMapping
    fun createAccount(@RequestBody accountRequest: AccountRequest): ResponseEntity<Account> {
        val newAccount = accountService.createAccount(accountRequest)
        return ResponseEntity(newAccount, HttpStatus.CREATED)
    }



}
