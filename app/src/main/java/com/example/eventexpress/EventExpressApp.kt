package com.example.eventexpress

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventexpress.auth.ui.screens.AuthScreen

@Composable
fun EventExpressApp(modifier: Modifier=Modifier){
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = "Auth") {
        composable(route = "Auth"){
            AuthScreen(onSuccessfulSignIn = { navController.navigate("Home") })
        }
        composable(route = "Home"){
            Column {
                Text(text = "Home screen")
                Button(onClick = { navController.navigate("Auth") }) {
                    Text(text = "Back to auth")
                }
            }
           
        }
    }

}