package com.example.shoe_shop.data.model

data class VerifyRecoveryResponse(
    val success: Boolean,
    val message: String,
    val reset_token: String? = null
)