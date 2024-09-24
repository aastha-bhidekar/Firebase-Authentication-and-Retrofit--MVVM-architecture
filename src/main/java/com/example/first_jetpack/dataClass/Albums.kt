package com.example.first_jetpack.dataClass

data class Album(
    val rank: Int,
    val title: String,
    val description: String,
    val image: String,
    val big_image: String,
    val genre: List<String>,
    val thumbnail: String,
    val rating: String,
    val id: String,
    val year: Int,
    val imdb_link: String,
    val imdbid: String,
)


