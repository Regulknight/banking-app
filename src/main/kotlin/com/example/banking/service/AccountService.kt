package com.example.banking.service

import com.example.banking.dto.AccountRequest
import com.example.banking.exception.InvalidAccountIdException
import com.example.banking.model.Account
import com.example.banking.repository.AccountRepository
import com.example.banking.utils.SHA256Hashing
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val sha256Hashing: SHA256Hashing
) {

    @Transactional
    fun createAccount(accountRequest: AccountRequest): Account {
        val hashedPin = sha256Hashing.hashPin(accountRequest.pin)

        val account = Account(
            beneficiaryName = accountRequest.beneficiaryName,
            pin = hashedPin,
            balance = BigDecimal(0.0)
        )

        return accountRepository.save(account)
    }

    fun getAccountById(accountId: Long): Account {
        return accountRepository.findById(accountId).orElseThrow { InvalidAccountIdException("Account doesn't exist") }
    }

    fun getAccounts(): List<Account> {
        return accountRepository.findAll().toList()
    }

    fun updateAccount(account: Account) {
        accountRepository.save(account)
    }


}
