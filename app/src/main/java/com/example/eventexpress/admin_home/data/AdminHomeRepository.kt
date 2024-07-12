package com.example.eventexpress.admin_home.data

import com.example.eventexpress.admin_home.data.models.GetUsersResponse
import com.example.eventexpress.admin_home.data.models.UserCountResponse
import com.example.eventexpress.admin_home.network.AdminHomeAPI
import com.example.eventexpress.user_home.data.models.ChangeRoleRequest
import com.example.eventexpress.user_home.data.models.ChangeRoleResponse

interface AdminHomeRepository {
    suspend fun getUserCount(token: String): UserCountResponse
    suspend fun getOrganiserCount(token: String): UserCountResponse

    suspend fun getUsers(token: String): GetUsersResponse

    suspend fun getOrganisers(token: String): GetUsersResponse
    suspend fun changeRole(token: String, changeRoleRequest: ChangeRoleRequest): ChangeRoleResponse


}

class AdminHomeRepositoryImpl(private val adminHomeAPI: AdminHomeAPI) : AdminHomeRepository {
    override suspend fun getUserCount(token: String): UserCountResponse =
        adminHomeAPI.getUserCount(token)

    override suspend fun getOrganiserCount(token: String): UserCountResponse =
        adminHomeAPI.getOrganiserCount(token)

    override suspend fun getUsers(token: String): GetUsersResponse = adminHomeAPI.getUsers(token)

    override suspend fun getOrganisers(token: String): GetUsersResponse =
        adminHomeAPI.getOrganisers(token)

    override suspend fun changeRole(
        token: String,
        changeRoleRequest: ChangeRoleRequest
    ): ChangeRoleResponse = adminHomeAPI.changeRole(token, changeRoleRequest)
}