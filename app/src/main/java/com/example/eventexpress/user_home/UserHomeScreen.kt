package com.example.eventexpress.user_home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material.icons.filled.Tour
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Tour
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.eventexpress.data.User
import com.example.eventexpress.user_home.data.models.SavedEvents
import com.example.eventexpress.user_home.ui.Tabs
import com.example.eventexpress.user_home.ui.UserHomeViewModel
import com.example.eventexpress.user_home.ui.homeUIState
import com.example.eventexpress.R
import com.example.eventexpress.user_home.data.models.AllEvents
import com.example.eventexpress.user_home.ui.savedUIState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserHomeScreen(user: User, navController: NavHostController) {

    val userHomeViewModel: UserHomeViewModel = viewModel(factory = UserHomeViewModel.Factory)
    LaunchedEffect(Unit) {
        userHomeViewModel.setUser(user)
    }

    val currentTab = userHomeViewModel.currentTab.collectAsState()
    val userHomeUIState = userHomeViewModel.homeUIStateValue.collectAsState()
    val savedUIState = userHomeViewModel.savedUIStateValue.collectAsState()
    var expanded by rememberSaveable {
        mutableStateOf(false)
    }
    var logoutDialogExpanded by remember {
        mutableStateOf(false)
    }





    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = "Events")
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
                                onClick = { logoutDialogExpanded=true })
                        }

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
        },

        ) { innerPadding ->


        AnimatedVisibility(visible = currentTab.value == Tabs.HOME,
            enter = slideInHorizontally() { fullWidth ->
                -fullWidth
            },
            exit = slideOutHorizontally() { fullWidth ->
                -fullWidth
            }) {
            homescreen(userHomeUIState.value, innerPadding,navController)
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
        AnimatedVisibility(visible = currentTab.value == Tabs.YOUR_EVENTS,
            enter = slideInHorizontally() { fullWidth ->
                +fullWidth
            },
            exit = slideOutHorizontally() { fullWidth ->
                +fullWidth
            }) {
            savedScreen(innerPadding = innerPadding, value = savedUIState.value)
        }


    }


}

@Composable
fun savedScreen(innerPadding: PaddingValues, value: savedUIState) {

    when (value) {
        savedUIState.Error -> {
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
                    Text(text = "Error loading Events", color = MaterialTheme.colorScheme.error)
                }
            }
        }

        savedUIState.Loading -> {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }
        }

        is savedUIState.Success -> {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items((value as savedUIState.Success).savedEventsResponse.data) { event ->
                    SavedEventCard(event = event)
                }
            }

        }

    }
}


@Composable
fun homescreen(value: homeUIState, innerPadding: PaddingValues, navController: NavHostController) {
    when (value) {
        homeUIState.Error -> {
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
                    Text(text = "Error loading Events", color = MaterialTheme.colorScheme.error)
                }
            }
        }

        homeUIState.Loading -> {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }
        }


        is homeUIState.Success -> {
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                items((value as homeUIState.Success).getEventsResponse.data) { event ->
                    EventCard(event = event,navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedEventCard(event: SavedEvents) {
    val image = event.image.replace("http://", "https://")
    val date = event.startDate.substringBefore("T")
    val formattedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val finalDate = formattedDate.format(formatter)

    ElevatedCard(
        onClick = { /*TODO*/ }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "null",
                placeholder = painterResource(id = R.drawable.images),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(GenericShape { size, _ ->
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height / 1.2f)
                        lineTo(0f, 0f)

                    })
            )
        }

        Column(modifier = Modifier.height(200.dp), verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = event.title, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Icon(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = null
                )
                Text(text = finalDate, fontWeight = FontWeight.Light, fontSize = 15.sp)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = event.description, textAlign = TextAlign.Center)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Filled.Person2, contentDescription = null)
                Text(text = "Organised by: " + event.createdBy)
            }
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(event: AllEvents, navController: NavHostController) {
    val image = event.image.replace("http://", "https://")
    val date = event.startDate.substringBefore("T")
    val formattedDate = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val finalDate = formattedDate.format(formatter)

    ElevatedCard(
        onClick = { navController.navigate("event/${event._id}") }, modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            AsyncImage(
                model = image,
                contentDescription = "null",
                placeholder = painterResource(id = R.drawable.images),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(GenericShape { size, _ ->
                        lineTo(size.width, 0f)
                        lineTo(size.width, size.height)
                        lineTo(0f, size.height / 1.2f)
                        lineTo(0f, 0f)

                    })
            )
        }

        Column(modifier = Modifier.height(200.dp), verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = event.title, fontWeight = FontWeight.Bold, fontSize = 25.sp)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Icon(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    imageVector = Icons.Outlined.CalendarToday,
                    contentDescription = null
                )
                Text(text = finalDate, fontWeight = FontWeight.Light, fontSize = 15.sp)
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = event.description, textAlign = TextAlign.Center)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Icon(imageVector = Icons.Filled.Person2, contentDescription = null)
                Text(text = "Organised by: " + event.createdBy)
            }
        }

    }
}

//@Preview
//@Composable
//fun UserHomeScreenPreview() {
//    EventExpressTheme {
//        UserHomeScreen(
//            user = User(
//                "Hassaan Farooq",
//                "user",
//                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoiSm9obiBEb2UiLCJpZCI6IjY2NjgzMjA1YjdmOTZmOGQwZmI2NTc5MSIsInJvbGUiOiJvcmdhbml6ZXIiLCJjcmVhdGVkQXQiOiIyMDI0LTA3LTAxVDE5OjM5OjI5LjUyNVoiLCJpYXQiOjE3MTk4NjI3NjksImV4cCI6MTcxOTk0OTE2OX0._72BsYZ7or_i8REoQUCAjgUwxSf8GFj8dTRi_Pi3Fyk",
//                "1",
//                true
//            ),
//            navController = rememberNavController(),
//            clearUser =
//        )
//    }
//}
