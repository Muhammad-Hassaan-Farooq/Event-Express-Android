package com.example.eventexpress.user_home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Tour
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compose.EventExpressTheme
import com.example.eventexpress.data.User
import com.example.eventexpress.user_home.ui.Tabs
import com.example.eventexpress.user_home.ui.UserHomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(user: User) {

    val userHomeViewModel: UserHomeViewModel = viewModel(factory = UserHomeViewModel.Factory)
    val currentTab = userHomeViewModel.currentTab.collectAsState()

    println(currentTab.value)

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Events")
            },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                    }
                })
        },
        bottomBar = {

            NavigationBar(

            ) {
                NavigationBarItem(
                    selected = currentTab.value == Tabs.HOME,
                    onClick = { userHomeViewModel.changeTab(Tabs.HOME) },
                    icon = {
                        when (currentTab.value) {
                            Tabs.HOME -> Icon(Icons.Filled.Home, contentDescription = null)
                            Tabs.YOUR_EVENTS -> Icon(Icons.Outlined.Home, contentDescription = null)
                        }
                    },
                    label = {
                        Text(text = "Home")
                    })
                NavigationBarItem(
                    selected = currentTab.value == Tabs.YOUR_EVENTS,
                    onClick = { userHomeViewModel.changeTab(Tabs.YOUR_EVENTS) },
                    icon = {
                        when (currentTab.value) {
                            Tabs.HOME -> Icon(Icons.Outlined.Tour, contentDescription = null)
                            Tabs.YOUR_EVENTS -> Icon(Icons.Filled.Tour, contentDescription = null)
                        }
                    },
                    label = {
                        Text(text = "Your events")
                    }
                    )
            }
        }
    ) {

    }
}

@Preview
@Composable
fun UserHomeScreenPreview() {
    EventExpressTheme {
        UserHomeScreen(
            user = User(
                "Hassaan Farooq",
                "user",
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSm9obiBEb2UiLCJpZCI6IjY2NjgzMjA1YjdmOTZmOGQwZmI2NTc5MSIsInJvbGUiOiJvcmdhbml6ZXIiLCJjcmVhdGVkQXQiOiIyMDI0LTA3LTAxVDE5OjM5OjI5LjUyNVoiLCJpYXQiOjE3MTk4NjI3NjksImV4cCI6MTcxOTk0OTE2OX0._72BsYZ7or_i8REoQUCAjgUwxSf8GFj8dTRi_Pi3Fyk",
                "1",
                true
            )
        )
    }
}
