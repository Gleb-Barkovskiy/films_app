package com.example.filmsapp.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.filmsapp.FilmsApplication
import com.example.filmsapp.data.FilmsPreviewsRepository
import com.example.filmsapp.data.FilmsRepository
import com.example.filmsapp.model.FilmDetails
import com.example.filmsapp.model.FilmPreview
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface FilmsUiState {
    data class Success(val filmPreviews: List<FilmPreview>) : FilmsUiState
    data object Error : FilmsUiState
    data object Loading : FilmsUiState
}

sealed interface FilmDetailsUiState {
    data class Success(val filmDetails: FilmDetails) : FilmDetailsUiState
    data object Error : FilmDetailsUiState
    data object Loading : FilmDetailsUiState
}

class FilmsViewModel(
    private val filmsRepository: FilmsRepository,
    private val filmsPreviewsRepository: FilmsPreviewsRepository,
) : ViewModel() {

    var filmsUiState: FilmsUiState by mutableStateOf(FilmsUiState.Loading)
        private set

    var favoriteFilmsUiState: FilmsUiState by mutableStateOf(FilmsUiState.Loading)
        private set

    var filmDetailsUiState: FilmDetailsUiState by mutableStateOf(FilmDetailsUiState.Loading)
        private set


    init {
        getFilmsPreviews()
        getFavoriteFilms()
    }

    fun getFilmsPreviews() {
        viewModelScope.launch {
            filmsUiState = FilmsUiState.Loading
            filmsUiState = try {
                FilmsUiState.Success(filmsRepository.getFilmsPreviews())
            } catch (e: IOException) {
                FilmsUiState.Error
            } catch (e: HttpException) {
                FilmsUiState.Error
            }
        }
    }

    fun getFilmDetails(filmId: Long) {
        viewModelScope.launch {
            filmDetailsUiState = FilmDetailsUiState.Loading
            filmDetailsUiState = try {
                FilmDetailsUiState.Success(filmsRepository.getFilmDetails(filmId))
            } catch (e: IOException) {
                FilmDetailsUiState.Error
            } catch (e: HttpException) {
                FilmDetailsUiState.Error
            }
        }
    }

    fun addFavoriteFilm(filmPreview: FilmPreview) {
        viewModelScope.launch {
            favoriteFilmsUiState = try {
                filmsPreviewsRepository.insertItem(filmPreview)
                FilmsUiState.Success(filmsPreviewsRepository.getAllItemsStream().first())
            } catch (e: Exception) {
                FilmsUiState.Error
            }
        }
    }

    fun deleteFavoriteFilm(filmPreview: FilmPreview) {
        viewModelScope.launch {
            favoriteFilmsUiState = try {
                filmsPreviewsRepository.deleteItem(filmPreview)
                FilmsUiState.Success(filmsPreviewsRepository.getAllItemsStream().first())
            } catch (e: Exception) {
                FilmsUiState.Error
            }
        }
    }

    fun getFavoriteFilms() {
        viewModelScope.launch {
            favoriteFilmsUiState = FilmsUiState.Loading
            favoriteFilmsUiState = try {
                FilmsUiState.Success(filmsPreviewsRepository.getAllItemsStream().first())
            } catch (e: IOException) {
                FilmsUiState.Error
            } catch (e: HttpException) {
                FilmsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FilmsApplication)
                val filmsRepository = application.container.filmsRepository
                val filmsPreviewsRepository = application.container.filmsPreviewsRepository
                FilmsViewModel(
                    filmsRepository = filmsRepository,
                    filmsPreviewsRepository = filmsPreviewsRepository
                )
            }
        }
    }
}