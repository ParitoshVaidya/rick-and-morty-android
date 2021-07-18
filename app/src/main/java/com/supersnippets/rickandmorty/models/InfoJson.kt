package com.supersnippets.rickandmorty.models

data class InfoJson(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)