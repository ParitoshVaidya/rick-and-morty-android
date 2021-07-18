package com.supersnippets.rickandmorty.models

data class ResultJson(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: OriginJson,
    val location: LocationJson,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)