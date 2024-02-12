package com.example.filmsapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponse(
    @SerialName("films")
    val films: List<FilmPreview>
)