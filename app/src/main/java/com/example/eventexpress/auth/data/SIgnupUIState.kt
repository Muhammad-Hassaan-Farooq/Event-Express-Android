package com.example.eventexpress.auth.data

sealed class SignupUIState{
    object Idle:SignupUIState()
    object Loading:SignupUIState()
    data class Success(val signupResponse: SignupResponse):SignupUIState()
    data class Error(val message:String ):SignupUIState()
}