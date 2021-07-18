package com.supersnippets.rickandmorty.models.mapper

import com.supersnippets.rickandmorty.models.CharacterDto
import com.supersnippets.rickandmorty.models.ResultJson

class CharacterListMapper {

    fun toCharacterList(json: List<ResultJson>): List<CharacterDto> {
        with(json) {
            return if (this.isNotEmpty()) {
                this.map { toCharacterDto(it) }
            } else {
                emptyList()
            }
        }
    }

    fun toCharacterDto(result: ResultJson): CharacterDto {
        return CharacterDto(
            result.id,
            result.name,
            result.image,
            result.location.name,
            result.gender,
            result.status
        )
    }
}