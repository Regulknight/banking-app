package com.example.banking.controller

import com.example.banking.dto.TransactionResponse
import com.example.banking.exception.InsufficientFundsException
import com.example.banking.exception.InvalidPinException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler(value = [InvalidPinException::class])
    fun handleInvalidPinException(exception: InvalidPinException) :ResponseEntity<TransactionResponse> {
        return ResponseEntity(TransactionResponse(exception.message?:"Insufficient funds"), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [InsufficientFundsException::class])
    fun handleInsufficientFundsException(exception: InsufficientFundsException) :ResponseEntity<TransactionResponse> {
        return ResponseEntity(TransactionResponse(exception.message?:"Invalid pin"), HttpStatus.UNAUTHORIZED)
    }
}