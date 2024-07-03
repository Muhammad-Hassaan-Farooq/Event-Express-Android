package com.example.eventexpress.auth.data

sealed class LoginUIState {
    object Idle:LoginUIState()
    data class Success(val loginResponse: LoginResponse):LoginUIState()
    data class Error(val message:String):LoginUIState()
    object Loading:LoginUIState()
}