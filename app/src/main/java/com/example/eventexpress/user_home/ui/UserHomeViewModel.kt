package com.example.eventexpress.user_home.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eventexpress.EventExpressApplication
import com.example.eventexpress.data.User
import com.example.eventexpress.user_home.data.UserHomeRepository
import com.example.eventexpress.user_home.data.models.GetEventsResponse
import com.example.eventexpress.user_home.data.models.SavedEventsResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


enum class Tabs{
    HOME,
    YOUR_EVENTS,
}

sealed class homeUIState{
    data class Success(val getEventsResponse: GetEventsResponse):homeUIState()
    object Error:homeUIState()
    object Loading:homeUIState()
}

sealed class savedUIState{
    data class Success(val savedEventsResponse: SavedEventsResponse):savedUIState()
    object Error:savedUIState()
    object Loading:savedUIState()
}
class UserHomeViewModel(private val userHomeRepository: UserHomeRepository) :ViewModel(){
    private val _currentTab = MutableStateFlow<Tabs>(Tabs.HOME)
    val currentTab = _currentTab
    private val _homeUiState = MutableStateFlow<homeUIState>(homeUIState.Loading)
    val homeUIStateValue=_homeUiState
    private val _savedUiState = MutableStateFlow<savedUIState>(savedUIState.Loading)
    val savedUIStateValue = _savedUiState
    private val _user = MutableStateFlow<User>(User("","","","",false))



    fun setUser(user: User){
        if (!_user.value.isSet) {
            _user.value = user
            getEvents()
        }

    }

    fun getSavedEvents(){
        viewModelScope.launch {
            _savedUiState.value= savedUIState.Loading
            _savedUiState.value = try {
                val result = userHomeRepository.getSavedEvents("Bearer "+ _user.value.token)
                println(result)
                savedUIState.Success(result)
            }
            catch (e:Exception){
                savedUIState.Error
            }
        }
    }
    fun getEvents(){
        viewModelScope.launch {
            _homeUiState.value = homeUIState.Loading
            _homeUiState.value = try {
                val result = userHomeRepository.getEvents("Bearer "+ _user.value.token)
                homeUIState.Success(result)
            }
            catch (e:Exception){
                homeUIState.Error
            }
        }
    }

    fun changeTab(currentTab:Tabs){
        _currentTab.value = currentTab

        if(_currentTab.value == Tabs.YOUR_EVENTS){
            if (_savedUiState.value !is savedUIState.Success){
                getSavedEvents()
            }
        }
    }

    companion object{
        val Factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EventExpressApplication)
                val userHomeRepository = application.appContainer.userHomeRepository
                UserHomeViewModel(userHomeRepository)
            }
        }
    }
}