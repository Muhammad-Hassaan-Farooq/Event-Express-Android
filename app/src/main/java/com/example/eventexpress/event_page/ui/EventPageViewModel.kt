package com.example.eventexpress.event_page.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eventexpress.EventExpressApplication
import com.example.eventexpress.data.User
import com.example.eventexpress.event_page.data.EventPageRepository
import com.example.eventexpress.event_page.data.models.GetEventPageResponse
import com.example.eventexpress.user_home.data.models.GetEventsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class EventPageUiState() {
    data class Success(val getEventPageResponse: GetEventPageResponse) : EventPageUiState()
    object Error : EventPageUiState()
    object Loading : EventPageUiState()
}

class EventPageViewModel(private val eventPageRepository: EventPageRepository) : ViewModel() {

    private val _user = MutableStateFlow<User>(User("", "", "", "", false))
    private val _eventPageUIState = MutableStateFlow<EventPageUiState>(EventPageUiState.Loading)
    private val _eventID = MutableStateFlow<String>("")
    val eventPageUiState = _eventPageUIState



    fun setUser(user: User,eventId:String) {
        if (!_user.value.isSet) {
            _user.value = user
            _eventID.value = eventId
            getEventPage()
        }

    }

    fun getEventPage() {
        viewModelScope.launch { _eventPageUIState.value = EventPageUiState.Loading
        _eventPageUIState.value=try {
            val result = eventPageRepository.getEventPage(token = "Bearer "+ _user.value.token, eventId = _eventID.value)
            if (result.success) {
                EventPageUiState.Success(result)
            }
            else{
                EventPageUiState.Error
            }
        } catch (e:Exception){
            println(e)
            EventPageUiState.Error
        }}


    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventExpressApplication)
                val eventPageRepository = application.appContainer.eventPageRepository
                EventPageViewModel(eventPageRepository)
            }
        }
    }
}