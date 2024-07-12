package com.example.eventexpress.event_page.data.models

data class GetEventPageResponse(
    val success: Boolean,
    val data: EventData
)

data class EventData(
    val id: String,
    val event: String,
    val sections: Map<String, String>,
    val componentStates: Map<String, Map<String, Any>>,
    val version: Int
)

interface ComponentState
    data class HeroSection1(
        val hook: String,
        val title: String,
        val subtitle: String,
        val backgroundImage: String,
        val picture: String
    ) : ComponentState

    data class HeroSection2(
        val title: String,
        val subtitle: String,
        val backgroundImage: String,
    )
    data class TicketSection(
        val numberOfCards: Int,
        val cards: List<Card>
    ) : ComponentState

    data class TimelineSection1(
        val numberOfEvents:Int,
        val events:List<Event>
    ):ComponentState


data class Event(
    val date:String,
    val location:String,
    val time:String,
    val title: String,
    val desc:String
)

data class Card(
    val title: String,
    val subtitle: String,
    val price: Int,
    val perks: List<String>
)