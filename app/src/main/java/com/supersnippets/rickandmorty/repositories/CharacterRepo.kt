package com.supersnippets.rickandmorty.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.supersnippets.rickandmorty.helpers.ApiService
import com.supersnippets.rickandmorty.helpers.CharacterPagingSource
import com.supersnippets.rickandmorty.models.CharacterDto
import com.supersnippets.rickandmorty.models.mapper.CharacterListMapper
import kotlinx.coroutines.flow.Flow

class CharacterRepo(
    private val apiService: ApiService,
    private val mapper: CharacterListMapper
) {
    fun getCharacters(): Flow<PagingData<CharacterDto>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = true),
            pagingSourceFactory = { CharacterPagingSource(apiService, mapper) }
        ).flow
    }
}
