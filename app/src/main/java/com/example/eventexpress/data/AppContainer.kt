package com.example.eventexpress.data

import com.example.eventexpress.admin_home.data.AdminHomeRepository
import com.example.eventexpress.admin_home.data.AdminHomeRepositoryImpl
import com.example.eventexpress.admin_home.network.AdminHomeAPI
import com.example.eventexpress.user_home.data.UserHomeRepository
import com.example.eventexpress.user_home.data.UserHomeRepositoryImpl
import com.example.eventexpress.user_home.network.UserHomeAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val userHomeRepository:UserHomeRepository
    val adminHomeRepository:AdminHomeRepository
}

class DefaultAppContainer:AppContainer{
    private val baseUrl = "https://event-express-one.vercel.app/"
    private val retrofit:Retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl).build()

    private val userHomeRetrofit:UserHomeAPI by lazy {
        retrofit.create(UserHomeAPI::class.java)
    }
    private val adminHomeRetrofit:AdminHomeAPI by lazy {
        retrofit.create(AdminHomeAPI::class.java)
    }

    override val userHomeRepository: UserHomeRepository by lazy {
        UserHomeRepositoryImpl(userHomeRetrofit)
    }
    override val adminHomeRepository: AdminHomeRepository by lazy {
        AdminHomeRepositoryImpl(adminHomeRetrofit)
    }

}