package com.example.filmsapp.data

import com.example.filmsapp.model.FilmPreview
import kotlinx.coroutines.flow.Flow

interface FilmsPreviewsRepository {
    fun getAllItemsStream(): Flow<List<FilmPreview>>
    suspend fun insertItem(film: FilmPreview)
    suspend fun deleteItem(film: FilmPreview)
}