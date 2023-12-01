package com.example.banking

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class BankingApp

fun main(args: Array<String>) {
    runApplication<BankingApp>(*args)
}
