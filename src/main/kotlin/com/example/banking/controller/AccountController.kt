package com.example.banking.controller

import com.example.banking.dto.AccountRequest
import com.example.banking.dto.AccountResponse
import com.example.banking.model.Account
import com.example.banking.service.AccountService
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

    @GetMapping
    fun getAccounts(): ResponseEntity<List<AccountResponse>> {
        val accounts =
            accountService.getAccounts().stream().map { a -> AccountResponse(a.id!!, a.beneficiaryName, a.balance) }
                .toList()

        return ResponseEntity(accounts, HttpStatus.OK)
    }
}
