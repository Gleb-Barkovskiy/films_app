package com.example.filmsapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDetails(
    @SerialName("nameRu")
    val title: String,

    @SerialName("posterUrl")
    val posterUrl: String,

    @SerialName("description")
    val description: String,

    @SerialName("countries")
    val countries: List<Country>,

    @SerialName("genres")
    val genres: List<Genre>
)

@Serializable
data class Country(
    @SerialName("country")
    val country: String
)

@Serializable
data class Genre(
    @SerialName("genre")
    val genre: String
)

