package com.example.eventexpress.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.auth0.android.jwt.JWT
import com.example.eventexpress.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.io.encoding.Base64


class AppViewModel : ViewModel() {


    private val _user = MutableStateFlow<User>(
        User(
            "",
            "",
            "", "", false
        )
    )
    val user = _user

    fun clearUser() {
        _user.value = User("", "", "", "", false)
    }

    fun setUser(token: String, role: String) {


        val jwt = JWT(token)
        val name = jwt.getClaim("name").asString()
        val id = jwt.getClaim("id").asString()
        _user.value = User(name ?: "", role, token, id ?: "", true)

    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AppViewModel()
            }
        }
    }
}