package com.example.eventexpress.auth.network

import com.example.eventexpress.auth.data.LoginRequest
import com.example.eventexpress.auth.data.LoginResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthAPI {

    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest):LoginResponse
}