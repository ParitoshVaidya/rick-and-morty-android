package com.supersnippets.rickandmorty.helpers

import com.supersnippets.rickandmorty.models.CharactersResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    fun getCharacters(): Call<CharactersResponse>
}