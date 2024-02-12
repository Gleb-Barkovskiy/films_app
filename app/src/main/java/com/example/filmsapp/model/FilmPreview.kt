package com.example.filmsapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "items")
@Serializable
data class FilmPreview(
    @PrimaryKey
    @SerialName("filmId")
    val filmId: Long,

    @SerialName("nameRu")
    val title: String,

    @SerialName("year")
    val year: String,

    @SerialName("posterUrlPreview")
    val posterUrlPreview: String
)