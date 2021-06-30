package com.supersnippets.rickandmorty

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import com.supersnippets.rickandmorty.di.characterModule
import com.supersnippets.rickandmorty.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
        startKoin {
            androidContext(this@App)
            modules(listOf(networkModule, characterModule))
        }
    }
}