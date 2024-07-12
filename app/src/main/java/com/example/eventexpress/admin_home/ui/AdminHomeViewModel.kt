package com.example.eventexpress.admin_home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eventexpress.EventExpressApplication
import com.example.eventexpress.admin_home.data.AdminHomeRepository
import com.example.eventexpress.admin_home.data.models.GetUsersResponse
import com.example.eventexpress.data.User
import com.example.eventexpress.user_home.data.models.ChangeRoleRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


enum class Tabs{
    Organisers,
    Users
}

sealed class OrganiserPageUIState{
    data class Success(val response: GetUsersResponse):OrganiserPageUIState()
    object Error:OrganiserPageUIState()
    object Loading:OrganiserPageUIState()
}

sealed class UserPageUIState{
    data class Success(val response: GetUsersResponse):UserPageUIState()
    object Error:UserPageUIState()
    object Loading:UserPageUIState()
}


class AdminHomeViewModel(private val adminHomeRepository: AdminHomeRepository):ViewModel(){


    private val _currentTab = MutableStateFlow<Tabs>(Tabs.Organisers)
     val currentTab = _currentTab

    private val _organiserPageUIState = MutableStateFlow<OrganiserPageUIState>(OrganiserPageUIState.Loading)
    val organiserPageUIState = _organiserPageUIState

    private val _userPageUIState = MutableStateFlow<UserPageUIState>(UserPageUIState.Loading)
    val userPageUIState = _userPageUIState



    private val _user = MutableStateFlow<User>(User("","","","",false))
    private val _organiserCount = MutableStateFlow<Int>(0)
    val organiserCount = _organiserCount



    fun changeTab(tab:Tabs){
        if (_currentTab.value!=tab){
            _currentTab.value=tab
        }
        if(_currentTab.value == Tabs.Users ){
            if (_userPageUIState.value !is UserPageUIState.Success){
                getUsers()
            }
        }
    }
    fun setUser(user: User){
        if (!_user.value.isSet) {
            _user.value = user
            getOrganisers()
        }

    }

    fun changeRole(email:String,role:String){
        viewModelScope.launch {
            try {
                val response = adminHomeRepository.changeRole(token ="Bearer "+ _user.value.token, changeRoleRequest = ChangeRoleRequest(email = email, role = role) )
                if (response.success){
                    getUsers()
                    getOrganisers()
                }
                else{

                }
            }
            catch (e:Exception){
                TODO()
            }
        }
    }
    fun getOrganiserCount(){
        viewModelScope.launch {
            try {
                val response = adminHomeRepository.getOrganiserCount("Bearer "+ _user.value.token)
                _organiserCount.value = response.data
            }
            catch (e:Exception){
                TODO()
            }
        }

    }

    fun getOrganisers(){
        viewModelScope.launch {
            _organiserPageUIState.value= OrganiserPageUIState.Loading
            _organiserPageUIState.value =
                try {
                    val response = adminHomeRepository.getOrganisers("Bearer "+ _user.value.token)
                    if (response.success){
                        OrganiserPageUIState.Success(response)
                    }
                    else{
                        OrganiserPageUIState.Error
                    }
                }
                catch (e:Exception){

                    OrganiserPageUIState.Error
                }


        }
    }

    fun getUsers(){
        viewModelScope.launch {
            _userPageUIState.value = UserPageUIState.Loading
            _userPageUIState.value = try {
                val response = adminHomeRepository.getUsers("Bearer "+ _user.value.token)
                if (response.success){
                    UserPageUIState.Success(response)
                }
                else{
                    UserPageUIState.Error
                }
            }
            catch (e:Exception){
                UserPageUIState.Error
            }
        }
    }


    companion object {
        val Factory:ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as EventExpressApplication)
                val adminHomeRepository = application.appContainer.adminHomeRepository
                AdminHomeViewModel(adminHomeRepository)
            }
        }
    }
}