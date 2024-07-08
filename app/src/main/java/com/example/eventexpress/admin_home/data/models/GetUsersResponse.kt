package com.example.eventexpress.admin_home.data.models

data class GetUsersResponse(
    val `data`: List<User>,
    val message: String,
    val success: Boolean
)

data class User(
    val __v: Int,
    val _id: String,
    val attendingEvents: List<Any>,
    val createdAt: String,
    val email: String,
    val events: List<String>,
    val firstName: String,
    val isDeleted: Boolean,
    val lastName: String,
    val role: String,
    val status: String,
    val updatedAt: String,
    val updatedBy: String
)