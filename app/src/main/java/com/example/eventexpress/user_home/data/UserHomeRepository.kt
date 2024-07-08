package com.example.eventexpress.user_home.data

import com.example.eventexpress.user_home.data.models.GetEventsResponse
import com.example.eventexpress.user_home.data.models.SavedEventsResponse
import com.example.eventexpress.user_home.network.UserHomeAPI

interface UserHomeRepository {
    suspend fun getEvents(token: String):GetEventsResponse
    suspend fun getSavedEvents(token:String):SavedEventsResponse
    fun repoTest()
}

class UserHomeRepositoryImpl(private val userHomeAPI: UserHomeAPI):UserHomeRepository{
    override suspend fun getEvents(token:String):GetEventsResponse =userHomeAPI.getEvents(token)
    override suspend fun getSavedEvents(token: String): SavedEventsResponse =userHomeAPI.getSavedEvents(token)
    override fun repoTest(){
        println("Repository is working")
    }
}