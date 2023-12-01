package com.example.banking.utils

import org.springframework.stereotype.Component
import java.security.MessageDigest

@Component
open class SHA256Hashing() {
    private val messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256")

    fun hashPin(pin: String): String {
        val hashedByteArray = messageDigest.digest(pin.encodeToByteArray())
        return with(StringBuilder()) {
            hashedByteArray.forEach { b -> append(String.format("%02X", b))}
            toString()
        }
    }
}