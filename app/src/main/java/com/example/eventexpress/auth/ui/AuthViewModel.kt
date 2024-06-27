package com.example.eventexpress.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eventexpress.auth.data.LoginRequest
import com.example.eventexpress.auth.data.LoginResponse
import com.example.eventexpress.auth.data.LoginUIState
import com.example.eventexpress.auth.network.AuthAPI
import com.example.eventexpress.auth.network.AuthAPIInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {

    private val authApi = AuthAPIInstance.createAuthAPI(AuthAPI::class.java)
    private val _currentScreen = MutableStateFlow("login")
    val currentScreen: StateFlow<String> = _currentScreen

    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val loginUIState = _loginUIState



    fun changeScreen(screen: String) {
        if (_currentScreen.value != screen) {
            _currentScreen.value = screen
        }
    }


    suspend fun login(email:String,password:String):LoginResponse {
        val response = authApi.login(LoginRequest(email,password))

        println(response)
        return response
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel()
            }
        }
    }
}