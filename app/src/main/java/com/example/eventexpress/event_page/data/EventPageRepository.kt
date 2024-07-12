package com.example.eventexpress.event_page.data

import com.example.eventexpress.event_page.data.models.GetEventPageResponse
import com.example.eventexpress.event_page.network.EventPageAPI

interface EventPageRepository {
    suspend fun getEventPage(token: String, eventId: String): GetEventPageResponse
}

class EventPageRepositoryImpl(private val eventPageAPI: EventPageAPI) : EventPageRepository {
    override suspend fun getEventPage(token: String, eventId: String): GetEventPageResponse =
        eventPageAPI.getEventPage(token = token, eventId = eventId)

}