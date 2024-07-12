package com.example.eventexpress

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventexpress.admin_home.AdminHomeScreen
import com.example.eventexpress.auth.ui.screens.AuthScreen
import com.example.eventexpress.event_page.EventPage
import com.example.eventexpress.ui.AppViewModel
import com.example.eventexpress.user_home.UserHomeScreen

@Composable
fun EventExpressApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val appViewModel: AppViewModel = viewModel(factory = AppViewModel.Factory)

    fun setUser(token: String, role: String) {
        appViewModel.setUser(token, role)
    }
    println(appViewModel.user.value.role)
    fun destination(): String {

        when (appViewModel.user.value.role) {
            "user" -> return "user_home"
            "admin" -> return "admin_home"
            "organizer" -> return "organiser_home"
        }

        return "Auth"

    }


    NavHost(
        navController = navController,
        startDestination = if (appViewModel.user.value.isSet) destination() else "Auth"
    ) {
        composable(route = "Auth") {
            AuthScreen(
                onSuccessfulSignIn = { navController.navigate(if (appViewModel.user.value.isSet) destination() else "Auth") },
                setUser = ::setUser,
                clearUser = { appViewModel.clearUser() }
            )
        }
        composable(route = "user_home") {
            UserHomeScreen(user = appViewModel.user.value,navController)
        }
        composable(route = "admin_home") {
            AdminHomeScreen(user=appViewModel.user.value,navController)
        }
        composable(route = "organiser_home") {
            Column {
                Text(text = "You are an organiser")
            }
        }
        composable(route="event/{event_id}"){navBackStackEntry ->
            EventPage(navController=navController, eventId =  navBackStackEntry.arguments?.getString("event_id"),user=appViewModel.user.value)
        }
    }

}