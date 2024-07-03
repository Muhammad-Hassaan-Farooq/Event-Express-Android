package com.example.eventexpress.user_home.ui

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow


enum class Tabs{
    HOME,
    YOUR_EVENTS,

}
class UserHomeViewModel :ViewModel(){
    private val _currentTab = MutableStateFlow<Tabs>(Tabs.HOME)
    val currentTab = _currentTab

    fun changeTab(currentTab:Tabs){
        _currentTab.value = currentTab
    }

    companion object{
        val Factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                UserHomeViewModel()
            }
        }
    }
}