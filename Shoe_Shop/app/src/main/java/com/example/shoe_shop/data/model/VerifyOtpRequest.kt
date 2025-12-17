// VerifyOtpRequest.kt
package com.example.shoe_shop.data.model

data class VerifyOtpRequest(
    val email: String,
    val token: String,
    val type: String = "email"
)