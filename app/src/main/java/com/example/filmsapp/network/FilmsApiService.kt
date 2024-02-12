package com.example.filmsapp.network

import com.example.filmsapp.model.FilmDetails
import com.example.filmsapp.model.FilmsResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface FilmsApiService {
    @Headers("x-api-key: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("top?type=TOP_100_POPULAR_FILMS")
    suspend fun getFilmsPreviews(): FilmsResponse

    @Headers("x-api-key: e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
    @GET("{id}")
    suspend fun getFilmDetails(@Path("id") filmId: Long): FilmDetails
}