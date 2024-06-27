package com.example.eventexpress.auth.data

data class LoginResponse(
    val message: String,
    val role: String?,
    val success: Boolean,
    val token: String?
)