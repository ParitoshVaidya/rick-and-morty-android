package com.supersnippets.rickandmorty.models

import com.supersnippets.rickandmorty.db.CharacterEntity

data class Results(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
) {
    fun toCharacterDto() = CharactersDto(id, name, image, location.name, gender, status)
    fun toCharacterEntity() = CharacterEntity(id, name, image, location.name, gender, status)
}