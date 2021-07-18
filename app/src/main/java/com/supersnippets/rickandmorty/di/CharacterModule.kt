package com.supersnippets.rickandmorty.di

import com.supersnippets.rickandmorty.CharacterViewModel
import com.supersnippets.rickandmorty.db.AppDatabase
import com.supersnippets.rickandmorty.models.mapper.CharacterListMapper
import com.supersnippets.rickandmorty.repositories.CharacterRepo
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterModule = module {
    single { CharacterRepo(get(), get()) }
    viewModel { CharacterViewModel(get()) }
    factory {
        AppDatabase.getInstance(androidContext()).characterDao()
    }
    factory {
        CharacterListMapper()
    }
}