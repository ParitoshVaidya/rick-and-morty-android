package com.supersnippets.rickandmorty.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDto(
    val id: Long?,
    val name: String,
    val image: String,
    val location: String,
    val gender: String,
    val status: String
) : Parcelable