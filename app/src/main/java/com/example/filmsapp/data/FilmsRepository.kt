package com.example.filmsapp.data

import com.example.filmsapp.model.FilmDetails
import com.example.filmsapp.model.FilmPreview
import com.example.filmsapp.network.FilmsApiService

interface FilmsRepository {
    suspend fun getFilmsPreviews(): List<FilmPreview>
    suspend fun getFilmDetails(filmId: Long): FilmDetails
}

class NetworkFilmsRepository(
    private val filmsApiService: FilmsApiService
) : FilmsRepository {
    override suspend fun getFilmsPreviews(): List<FilmPreview> = filmsApiService.getFilmsPreviews().films
    override suspend fun getFilmDetails(filmId: Long): FilmDetails = filmsApiService.getFilmDetails(filmId)
}