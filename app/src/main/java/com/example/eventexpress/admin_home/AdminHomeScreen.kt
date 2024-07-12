package com.example.eventexpress.admin_home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPinCircle
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.PersonPinCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.eventexpress.admin_home.ui.AdminHomeViewModel
import com.example.eventexpress.admin_home.ui.OrganiserPageUIState
import com.example.eventexpress.admin_home.ui.Tabs
import com.example.eventexpress.admin_home.ui.UserPageUIState
import com.example.eventexpress.data.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminHomeScreen(user: User, navController: NavHostController) {

    val adminHomeViewModel: AdminHomeViewModel = viewModel(factory = AdminHomeViewModel.Factory)
    val currentTab = adminHomeViewModel.currentTab.collectAsState()
    val organiserPageUIState = adminHomeViewModel.organiserPageUIState.collectAsState()
    val userPageUIState = adminHomeViewModel.userPageUIState.collectAsState()
    var logoutDialogExpanded by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit) {
        adminHomeViewModel.setUser(user)
        adminHomeViewModel.getOrganiserCount()
    }
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Admin Page")
            },
                actions = {
                    Box(modifier = Modifier) {
                        IconButton(onClick = { expanded = !expanded }) {
                            Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                        }
                        DropdownMenu(
                            modifier = Modifier.width(120.dp),
                            expanded = expanded,
                            onDismissRequest = { expanded = false }) {
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Profile",
                                        textAlign = TextAlign.Center
                                    )
                                },
                                onClick = { /*TODO*/ })
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Settings",
                                        textAlign = TextAlign.Center
                                    )
                                },
                                onClick = { /*TODO*/ })
                            DropdownMenuItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(),
                                        text = "Logout",
                                        textAlign = TextAlign.Center
                                    )
                                },
                                onClick = { logoutDialogExpanded = true })
                        }

                    }
                })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentTab.value == Tabs.Organisers,
                    onClick = { adminHomeViewModel.changeTab(Tabs.Organisers) },
                    label = {
                        Text(text = "Organisers")
                    },
                    icon = {
                        when (currentTab.value) {
                            Tabs.Organisers -> Icon(
                                imageVector = Icons.Filled.PersonPinCircle,
                                contentDescription = null
                            )

                            Tabs.Users -> Icon(
                                imageVector = Icons.Outlined.PersonPinCircle,
                                contentDescription = null
                            )
                        }
                    })
                NavigationBarItem(
                    selected = currentTab.value == Tabs.Users,
                    onClick = { adminHomeViewModel.changeTab(Tabs.Users) },
                    label = { Text(text = "Users") },
                    icon = {
                        when (currentTab.value) {
                            Tabs.Organisers -> Icon(
                                imageVector = Icons.Filled.People,
                                contentDescription = null
                            )

                            Tabs.Users -> Icon(
                                imageVector = Icons.Outlined.People,
                                contentDescription = null
                            )
                        }
                    })
            }

        }
    ) { innerPadding ->
        AnimatedVisibility(visible = currentTab.value == Tabs.Organisers,
            enter = slideInHorizontally() { fullWidth ->
                -fullWidth
            },
            exit = slideOutHorizontally() { fullWidth ->
                -fullWidth
            }) {
            OrganiserPage(organiserPageUIState.value, innerPadding, adminHomeViewModel)
        }

        if (logoutDialogExpanded) {
            AlertDialog(onDismissRequest = { logoutDialogExpanded = false }, confirmButton = {
                TextButton(
                    onClick = {expanded=false;logoutDialogExpanded=false; navController.navigate("Auth") }) {
                    Text(text = "Logout")
                }
            },
                dismissButton = {
                    TextButton(onClick = { logoutDialogExpanded = false }) {
                        Text(text = "Cancel")
                    }
                },
                title = {
                    Text(text = "Logout")
                },
                text = {
                    Text(text = "Are you sure you want to log out?")
                })
        }

        AnimatedVisibility(visible = currentTab.value == Tabs.Users,
            enter = slideInHorizontally() { fullWidth ->
                +fullWidth
            },
            exit = slideOutHorizontally() { fullWidth ->
                +fullWidth
            }) {
            UserPage(userPageUIState.value, innerPadding, adminHomeViewModel)
        }

    }
}


@Composable
fun UserPage(
    userPageUIState: UserPageUIState,
    innerPadding: PaddingValues,
    adminHomeViewModel: AdminHomeViewModel
) {
    when (userPageUIState) {
        UserPageUIState.Error -> {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.Error, contentDescription = null)
                    Text(text = "Error loading Users", color = MaterialTheme.colorScheme.error)
                }
            }
        }

        UserPageUIState.Loading -> {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }
        }

        is UserPageUIState.Success -> {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(userPageUIState.response.data) { user ->
                    UserCard(user, adminHomeViewModel)

                }
            }
        }
    }

}

@Composable
fun OrganiserPage(
    organiserPageUIState: OrganiserPageUIState,
    innerPadding: PaddingValues,
    adminHomeViewModel: AdminHomeViewModel
) {
    when (organiserPageUIState) {
        OrganiserPageUIState.Error -> {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Filled.Error, contentDescription = null)
                    Text(text = "Error loading Organisers", color = MaterialTheme.colorScheme.error)
                }
            }
        }

        OrganiserPageUIState.Loading -> {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }
        }

        is OrganiserPageUIState.Success -> {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items(organiserPageUIState.response.data) { organiser ->
                    OrganiserCard(organiser, adminHomeViewModel)

                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrganiserCard(
    organiser: com.example.eventexpress.admin_home.data.models.User,
    adminHomeViewModel: AdminHomeViewModel
) {
    ElevatedCard(
        onClick = { /*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(text = organiser.firstName + organiser.lastName)
            Text(text = organiser.email)
            Button(onClick = { adminHomeViewModel.changeRole(organiser.email, "user") }) {
                Text(text = "Change Role to User")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserCard(
    organiser: com.example.eventexpress.admin_home.data.models.User,
    adminHomeViewModel: AdminHomeViewModel
) {
    ElevatedCard(
        onClick = { /*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )
            Text(text = organiser.firstName + organiser.lastName)
            Text(text = organiser.email)
            Button(onClick = { adminHomeViewModel.changeRole(organiser.email, "organizer") }) {
                Text(text = "Change Role to Organiser")
            }
        }

    }
}