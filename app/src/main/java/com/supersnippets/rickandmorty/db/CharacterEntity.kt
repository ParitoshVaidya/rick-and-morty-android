package com.supersnippets.rickandmorty.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "characters"
)
data class CharacterEntity(
    @PrimaryKey val id: Long?,
    val name: String,
    val image: String,
    val location: String,
    val gender: String,
    val status: String
)