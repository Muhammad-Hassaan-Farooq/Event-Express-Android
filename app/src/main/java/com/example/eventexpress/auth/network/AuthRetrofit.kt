package com.example.eventexpress.auth.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class AuthAPIInstance{
    companion object{
        private val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://event-express-one.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create()).build()


        fun <T> createAuthAPI(apiClass:Class<T>):T{
            return retrofit.create(apiClass)
        }
     }
}