package com.example.filmsapp.data

import com.example.filmsapp.model.FilmPreview
import kotlinx.coroutines.flow.Flow

class OfflineItemsRepository(private val filmPreviewDao: FilmPreviewDao) : FilmsPreviewsRepository {
    override fun getAllItemsStream(): Flow<List<FilmPreview>> = filmPreviewDao.getAllFilms()

    override suspend fun insertItem(film: FilmPreview) = filmPreviewDao.insert(film)

    override suspend fun deleteItem(film: FilmPreview) = filmPreviewDao.delete(film)
}