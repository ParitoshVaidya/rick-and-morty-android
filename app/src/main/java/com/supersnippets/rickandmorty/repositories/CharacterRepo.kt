package com.supersnippets.rickandmorty.repositories;

import androidx.lifecycle.MutableLiveData
import com.supersnippets.rickandmorty.db.CharacterDao
import com.supersnippets.rickandmorty.db.CharacterEntity
import com.supersnippets.rickandmorty.helpers.ApiService;
import com.supersnippets.rickandmorty.helpers.NoInternetException
import com.supersnippets.rickandmorty.models.CharactersDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CharacterRepo(private val apiService: ApiService, private val characterDao: CharacterDao) :
    BaseRepository() {

    fun getCharacters(): MutableLiveData<List<CharactersDto>> {
        val data = MutableLiveData<List<CharactersDto>>()

        apiService.getCharacters().makeCall {
            onResponseSuccess = { it ->
                if (it.body()?.results?.isNotEmpty()!!) {
                    val characters = ArrayList<CharactersDto>()
                    val characterEntities = ArrayList<CharacterEntity>()
                    it.body()?.results?.map { characterModel ->
                        characters.add(characterModel.toCharacterDto())
                        characterEntities.add(characterModel.toCharacterEntity())
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        characterDao.insertAll(characterEntities)
                    }
                    data.value = characters
                }
            }
            onResponseFailure = { it ->
                if (it is NoInternetException) {
                    val characters = ArrayList<CharactersDto>()
                    CoroutineScope(Dispatchers.IO).launch {
                        val characterEntities = characterDao.getAll()
                        characterEntities.map { entity ->
                            characters.add(entity.toCharacterDto())
                        }
                        withContext(Dispatchers.Main) {
                            data.value = characters
                        }
                    }
                }
            }
        }
        return data
    }
}
