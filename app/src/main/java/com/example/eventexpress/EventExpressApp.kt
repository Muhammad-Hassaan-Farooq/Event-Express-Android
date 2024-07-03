package com.example.eventexpress

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventexpress.auth.ui.screens.AuthScreen
import com.example.eventexpress.ui.AppViewModel
import com.example.eventexpress.user_home.UserHomeScreen

@Composable
fun EventExpressApp(modifier: Modifier=Modifier){
    val navController = rememberNavController()
    val appViewModel:AppViewModel = viewModel(factory = AppViewModel.Factory)

    fun setUser(token:String,role: String){
        appViewModel.setUser(token,role)
    }
    fun destination():String{

            when (appViewModel.user.value.role) {
                "user" -> return "user_home"
                "admin" -> return "admin_home"
                "organizer" -> return "organiser_home"
            }

        return "Auth"

    }


    NavHost(navController = navController, startDestination = if(appViewModel.user.value.isSet)destination() else "Auth") {
        composable(route = "Auth"){
            AuthScreen(onSuccessfulSignIn = { navController.navigate(if(appViewModel.user.value.isSet)destination() else "Auth") }, setUser = ::setUser)
        }
        composable(route = "user_home"){
            UserHomeScreen(user = appViewModel.user.value)
        }
        composable(route = "admin_home"){

        }
        composable(route = "organiser_home"){

        }
    }

}