package com.example.eventexpress

import android.app.Application
import com.example.eventexpress.data.DefaultAppContainer

class EventExpressApplication:Application() {
    val appContainer=DefaultAppContainer()
}