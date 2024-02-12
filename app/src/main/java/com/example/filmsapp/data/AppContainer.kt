package com.example.filmsapp.data

import android.content.Context
import com.example.filmsapp.network.FilmsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val filmsRepository: FilmsRepository
    val filmsPreviewsRepository: FilmsPreviewsRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://kinopoiskapiunofficial.tech/api/v2.2/films/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(
            json.asConverterFactory("application/json".toMediaType())
        )
        .baseUrl(baseUrl)
        .build()


    private val retrofitService: FilmsApiService by lazy {
        retrofit.create(FilmsApiService::class.java)
    }

    override val filmsRepository: FilmsRepository by lazy {
        NetworkFilmsRepository(retrofitService)
    }

    override val filmsPreviewsRepository: FilmsPreviewsRepository by lazy {
        OfflineItemsRepository(InventoryDatabase.getDatabase(context).filmsPreviewsDao())
    }

}
