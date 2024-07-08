package com.example.eventexpress.user_home.network

import com.example.eventexpress.user_home.data.models.GetEventsResponse
import com.example.eventexpress.user_home.data.models.SavedEventsResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface UserHomeAPI {
    @GET("event/getEvents")
    suspend fun getEvents(@Header("Authorization")token:String):GetEventsResponse

    @GET("event/myCurrentEvents")
    suspend fun getSavedEvents(@Header("Authorization")token: String):SavedEventsResponse
}