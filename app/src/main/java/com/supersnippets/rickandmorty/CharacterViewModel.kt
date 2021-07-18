package com.supersnippets.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.supersnippets.rickandmorty.repositories.CharacterRepo

class CharacterViewModel(
    characterRepo: CharacterRepo
) : ViewModel() {
    val characters = characterRepo.getCharacters().cachedIn(viewModelScope)
}