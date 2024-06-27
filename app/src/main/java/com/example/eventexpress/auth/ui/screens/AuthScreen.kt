package com.example.eventexpress.auth.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eventexpress.auth.ui.AuthViewModel

@Composable
fun AuthScreen(modifier: Modifier = Modifier, onSuccessfulSignIn: () -> Unit) {

    val authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
    val currentScreen = authViewModel.currentScreen.collectAsState()


    fun handleLogin(email: String, password: String) {

    }



    AnimatedVisibility(
        visible = currentScreen.value == "login",
        enter = slideInHorizontally() { fullWidth ->
            -fullWidth
        },
        exit = slideOutHorizontally() { fullWidth ->
            -fullWidth
        }
    ) {
        LoginScreen(
            modifier = modifier,
            changeScreen = { authViewModel.changeScreen("signup") },
            onSuccessfulLogin = onSuccessfulSignIn,
            loginState = authViewModel.loginUIState
        )
    }



    AnimatedVisibility(
        visible = currentScreen.value == "signup",
        enter = slideInHorizontally() { fullWidth ->
            +fullWidth
        },
        exit = slideOutHorizontally() { fullWidth ->
            +fullWidth
        }
    ) {
        SignupScreen(modifier = modifier, changeScreen = { authViewModel.changeScreen("login") })
    }

}