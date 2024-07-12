package com.example.eventexpress.event_page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size.Companion.ORIGINAL
import com.example.eventexpress.data.User
import com.example.eventexpress.event_page.data.models.GetEventPageResponse
import com.example.eventexpress.event_page.data.models.HeroSection1
import com.example.eventexpress.event_page.data.models.HeroSection2
import com.example.eventexpress.event_page.ui.EventPageUiState
import com.example.eventexpress.event_page.ui.EventPageViewModel

@Composable
fun EventPage(navController: NavHostController, eventId: String?, user: User) {
    if (eventId == null) {
        navController.navigate("user_home")
    } else {
        val eventPageViewModel: EventPageViewModel = viewModel(factory = EventPageViewModel.Factory)
        val eventPageUiState = eventPageViewModel.eventPageUiState.collectAsState()

        LaunchedEffect(Unit) {
            eventPageViewModel.setUser(user, eventId)
        }


        when (eventPageUiState.value) {
            EventPageUiState.Error -> {
                var showDialog by remember {
                    mutableStateOf(true)
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = {
                            showDialog = false; navController.navigate("user_home")
                        },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialog = false;navController.navigate("user_home")
                            }) {
                                Text(text = "Return")
                            }
                        },
                        title = {
                            Text(text = "Error loading event")
                        })
                }
            }

            EventPageUiState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { CircularProgressIndicator() }
            }

            is EventPageUiState.Success -> {
                LoadedEventPage((eventPageUiState.value as EventPageUiState.Success).getEventPageResponse)
            }
        }
    }
}

@Composable
fun LoadedEventPage(eventPageResponse: GetEventPageResponse) {
    Scaffold(

    ) { innerPadding ->
        LazyColumn(modifier = Modifier.padding(innerPadding)) {
            eventPageResponse.data.sections["Section1"]?.let { it ->
                item {
                    PageSection(it, eventPageResponse.data.componentStates["Section1"])
                }

            }
            eventPageResponse.data.sections["Section2"]?.let { it ->
                item {
                    PageSection(it, eventPageResponse.data.componentStates["Section2"])
                }

            }
            eventPageResponse.data.sections["Section3"]?.let { it ->
                item {
                    PageSection(it, eventPageResponse.data.componentStates["Section3"])
                }

            }
            eventPageResponse.data.sections["Section4"]?.let { it ->
                item {
                    PageSection(it, eventPageResponse.data.componentStates["Section4"])
                }

            }
            eventPageResponse.data.sections["Section5"]?.let { it ->
                item {
                    PageSection(it, eventPageResponse.data.componentStates["Section5"])
                }

            }
        }


    }
}

@Composable
fun PageSection(s: String, map: Map<String, Any>?) {
    if (map != null) {

        val elementName = map.keys.first()
        val componentState = map[elementName] as? Map<String, Any>
        when (elementName) {
            "HeroSection1" -> {
                val state = HeroSection1(
                    hook = componentState?.get("hook") as? String ?: "",
                    title = componentState?.get("title") as? String ?: "",
                    subtitle = componentState?.get("subtitle") as? String ?: "",
                    backgroundImage = componentState?.get("backgroundImage") as? String ?: "",
                    picture = componentState?.get("picture") as? String ?: ""
                )


                HeroSection1Composable(state)
            }

            "HeroSection2" -> {
                val state = HeroSection2(
                    title = componentState?.get("title") as? String ?: "",
                    subtitle = componentState?.get("subtitle") as? String ?: "",
                    backgroundImage = componentState?.get("backgroundImage") as? String ?: "",
                )
                HeroSection2Composable(state = state)
            }

            "TimelineSection1" -> {

            }

            "TicketSection4" -> {

            }
        }

    }


}

@Composable
fun HeroSection1Composable(state: HeroSection1) {
    val url = state.backgroundImage
    val start = url.indexOf("'") + 1
    val end = url.lastIndexOf("'")
    val extractedURL = url.substring(start, end)

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(extractedURL)
            .size(ORIGINAL)
            .build(), contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .paint(
                painter, contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = state.hook, fontWeight = FontWeight.Light, fontSize = 15.sp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(0.5f),
                text = state.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            AsyncImage(
                modifier = Modifier.weight(0.5f),
                model = state.picture,
                contentDescription = null
            )
        }
        Text(text = state.subtitle, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)


    }
}

@Composable
fun HeroSection2Composable(state: HeroSection2) {
    val url = state.backgroundImage
    val start = url.indexOf("'") + 1
    val end = url.lastIndexOf("'")
    val extractedURL = url.substring(start, end)
    println(extractedURL)
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(extractedURL)
            .size(ORIGINAL)
            .build(), contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier
            .height(400.dp)
            .fillMaxWidth()
            .paint(
                painter, contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            Text(
                text = state.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Text(text = state.subtitle, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center)


    }
}