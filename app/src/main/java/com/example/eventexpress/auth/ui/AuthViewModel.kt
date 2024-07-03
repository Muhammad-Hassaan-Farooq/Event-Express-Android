package com.example.eventexpress.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eventexpress.auth.data.LoginRequest
import com.example.eventexpress.auth.data.LoginUIState
import com.example.eventexpress.auth.data.SignupRequest
import com.example.eventexpress.auth.data.SignupUIState
import com.example.eventexpress.auth.network.AuthAPI
import com.example.eventexpress.auth.network.AuthAPIInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val authApi = AuthAPIInstance.createAuthAPI(AuthAPI::class.java)
    private val _currentScreen = MutableStateFlow("login")
    val currentScreen: StateFlow<String> = _currentScreen

    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Idle)
    val loginUIState = _loginUIState

    private val _signupUIState = MutableStateFlow<SignupUIState>(SignupUIState.Idle)
    val signupUIState = _signupUIState


    fun changeScreen(screen: String) {
        if (_currentScreen.value != screen) {
            _currentScreen.value = screen
        }
    }

    fun changeLoginUiState(uiState: LoginUIState) {
        if (_loginUIState.value != uiState) {
            _loginUIState.value = uiState
        }
    }

    fun signup(fName: String, lName: String, email: String, password: String) {

        viewModelScope.launch {
            _signupUIState.value = SignupUIState.Loading
            try {
                val response = authApi.signup(
                    SignupRequest(
                        email = email,
                        firstName = fName,
                        lastName = lName,
                        role = "user",
                        password = password
                    )
                )
                if(response.success){
                    _signupUIState.value=SignupUIState.Success(response)
                }
                else{
                    _signupUIState.value = SignupUIState.Error(message = response.message)
                }
            }
            catch (e:Exception){
                _signupUIState.value= SignupUIState.Error(e.message?:"Unknown Error")
            }
        }
    }


    fun clearError() {
        _loginUIState.value = LoginUIState.Idle
    }

    fun clearSignupError(){
        _signupUIState.value=SignupUIState.Idle
    }

    fun login(email: String, password: String,setUser:(String,String)->Unit) {
        _loginUIState.value = LoginUIState.Loading
        viewModelScope.launch {
            _loginUIState.value = LoginUIState.Loading
            try {
                val response = authApi.login(LoginRequest(email, password))
                if (response.success) {
                    _loginUIState.value = LoginUIState.Success(response)
                    setUser(response.token,response.role)
                } else {
                    _loginUIState.value = LoginUIState.Error(message = response.message)
                }
            } catch (e: Exception) {
                _loginUIState.value = LoginUIState.Error(e.message ?: "Unknown error")
            }


        }


    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel()
            }
        }
    }
}