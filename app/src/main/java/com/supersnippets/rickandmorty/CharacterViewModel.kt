package com.supersnippets.rickandmorty

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.supersnippets.rickandmorty.models.CharactersDto
import com.supersnippets.rickandmorty.repositories.CharacterRepo

class CharacterViewModel(
    private val characterRepo: CharacterRepo
) : ViewModel() {
    var characters = MutableLiveData<List<CharactersDto>>()

    fun getCharacters() {
        characters = characterRepo.getCharacters()
    }
}