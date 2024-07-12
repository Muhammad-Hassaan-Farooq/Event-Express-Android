package com.example.eventexpress.event_page.network

import com.example.eventexpress.event_page.data.models.GetEventPageResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface EventPageAPI {
    @GET("eventPage/getEventPage/{event_id}")
    suspend fun getEventPage(
        @Path(value = "event_id", encoded = true) eventId: String,
        @Header("Authorization") token: String
    ): GetEventPageResponse
}