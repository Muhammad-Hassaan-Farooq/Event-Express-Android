package com.example.eventexpress.user_home.data.models

data class GetEventsResponse(
    val `data`: List<AllEvents>,
    val success: Boolean,
    val message:String?
)

data class AllEvents(
    val __v: Int,
    val _id: String,
    val attendees: List<Any>,
    val attendeesCount: Int,
    val attendeesLimit: Int,
    val createdAt: String,
    val createdBy: String,
    val deletedAt: Any,
    val deletedBy: Any,
    val description: String,
    val endDate: String,
    val image: String,
    val isDeleted: Boolean,
    val isFull: Boolean,
    val location: String,
    val organizer: String,
    val price: Int,
    val startDate: String,
    val title: String,
    val updatedAt: String,
    val updatedBy: String
)