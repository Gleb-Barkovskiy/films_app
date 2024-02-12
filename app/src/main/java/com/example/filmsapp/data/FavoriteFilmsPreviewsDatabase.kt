package com.example.filmsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmsapp.model.FilmPreview

@Database(entities = [FilmPreview::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun filmsPreviewsDao(): FilmPreviewDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "films_previews_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}