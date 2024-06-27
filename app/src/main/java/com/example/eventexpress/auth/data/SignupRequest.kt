package com.example.eventexpress.auth.data

data class SignupRequest(
    val email: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val role: String
)