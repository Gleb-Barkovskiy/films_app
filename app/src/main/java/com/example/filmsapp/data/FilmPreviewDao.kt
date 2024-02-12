package com.example.filmsapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmsapp.model.FilmPreview
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmPreviewDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(film: FilmPreview)

    @Delete
    suspend fun delete(film: FilmPreview)

    @Query("SELECT * from items")
    fun getAllFilms(): Flow<List<FilmPreview>>
}