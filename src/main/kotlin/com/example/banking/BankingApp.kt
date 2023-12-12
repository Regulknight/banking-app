package com.example.banking

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankingApp

fun main(args: Array<String>) {
    runApplication<BankingApp>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
