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
            "John Doe",
            "user",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSm9obiBEb2UiLCJpZCI6IjY2NjgzMjA1YjdmOTZmOGQwZmI2NTc5MSIsInJvbGUiOiJvcmdhbml6ZXIiLCJjcmVhdGVkQXQiOiIyMDI0LTA3LTAxVDE5OjM5OjI5LjUyNVoiLCJpYXQiOjE3MTk4NjI3NjksImV4cCI6MTcxOTk0OTE2OX0._72BsYZ7or_i8REoQUCAjgUwxSf8GFj8dTRi_Pi3Fyk",
            "66683205b7f96f8d0fb65791",
            true
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