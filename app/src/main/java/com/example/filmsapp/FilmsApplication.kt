package com.example.filmsapp

import android.app.Application
import com.example.filmsapp.data.AppContainer
import com.example.filmsapp.data.DefaultAppContainer

class FilmsApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}