package com.supersnippets.rickandmorty.helpers

import com.supersnippets.rickandmorty.models.CharactersJson
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int?): CharactersJson
}