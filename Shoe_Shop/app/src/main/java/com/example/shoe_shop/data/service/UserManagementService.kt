// UserManagementService.kt
package com.example.shoe_shop.data.service

import com.example.shoe_shop.data.model.SignInRequest
import com.example.shoe_shop.data.model.SignInResponse
import com.example.shoe_shop.data.model.SignUpRequest
import com.example.shoe_shop.data.model.SignUpResponse
import com.example.shoe_shop.data.model.VerifyOtpRequest
import com.example.shoe_shop.data.model.VerifyOtpResponse
import com.example.shoe_shop.data.model.VerifyRecoveryResponse
import com.example.shoe_shop.data.model.ChangePasswordRequest
import com.example.shoe_shop.data.model.ChangePasswordResponse
import com.example.shoe_shop.data.model.ForgotPasswordRequest
import com.example.shoe_shop.data.model.ForgotPasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT

const val API_KEY="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InlpeGlwdXh5b2ZwYWZudmJhcHJzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjU4NDM3OTMsImV4cCI6MjA4MTQxOTc5M30.-GHt_7WKFHWMzhN9MerHX7a3ZVW_IJDBIDmIxXW5gJ8"

interface UserManagementService {

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<SignUpResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/token?grant_type=password")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/verify")
    suspend fun verifyOtp(@Body verifyOtpRequest: VerifyOtpRequest): Response<VerifyOtpResponse>
    @POST("auth/verify-recovery-otp")
    suspend fun verifyRecoveryOtp(@Body request: VerifyOtpRequest): Response<VerifyRecoveryResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @POST("auth/v1/recover")
    suspend fun recoverPassword(
        @Body forgotPasswordRequest: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>

    @Headers(
        "apikey: $API_KEY",
        "Content-Type: application/json"
    )
    @PUT("auth/v1/user")
    suspend fun changePassword(
        @Header("Authorization") token: String, // Bearer токен пользователя
        @Body changePasswordRequest: ChangePasswordRequest
    ): Response<ChangePasswordResponse>
}