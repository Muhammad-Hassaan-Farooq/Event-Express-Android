package com.example.eventexpress.admin_home.network

import com.example.eventexpress.admin_home.data.models.GetUsersResponse
import com.example.eventexpress.admin_home.data.models.UserCountResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AdminHomeAPI {
    @GET("accountManagement/countUsers")
    suspend fun getUserCount(@Header("Authorization")token:String): UserCountResponse

    @GET("accountManagement/countOrganizers")
    suspend fun getOrganiserCount(@Header("Authorization")token: String): UserCountResponse

    @GET("accountManagement/getUsers")
    suspend fun getUsers(@Header("Authorization")token: String):GetUsersResponse

    @GET("accountManagement/getOrganizers")
    suspend fun getOrganisers(@Header("Authorization")token: String):GetUsersResponse
}