package com.supersnippets.rickandmorty.helpers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.supersnippets.rickandmorty.models.CharacterDto
import com.supersnippets.rickandmorty.models.mapper.CharacterListMapper

class CharacterPagingSource(
    private val apiService: ApiService,
    private val mapper: CharacterListMapper
) : PagingSource<Int, CharacterDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDto> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val jsonResponse = apiService.getCharacters(nextPage)
            val characterList = mapper.toCharacterList(jsonResponse.results)

            LoadResult.Page(
                data = characterList,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (jsonResponse.info.pages > nextPage) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int? {
        return null
    }
}