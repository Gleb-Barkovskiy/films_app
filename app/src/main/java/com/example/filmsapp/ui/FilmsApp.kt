package com.example.filmsapp.ui

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.filmsapp.R
import com.example.filmsapp.model.FilmPreview
import com.example.filmsapp.ui.screens.FilmDetailsScreen
import com.example.filmsapp.ui.screens.FilmsViewModel
import com.example.filmsapp.ui.screens.TopFilmsScreen

enum class FilmAppScreens(@StringRes val title: Int) {
    Top(title = R.string.top_title),
    Favorite(title = R.string.favorite_title),
    Details(title = R.string.details_title),;
}

@Composable
fun FilmsApp() {
    val navController = rememberNavController()

    val filmsViewModel: FilmsViewModel =
        viewModel(factory = FilmsViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = FilmAppScreens.Top.name
    ) {
        composable(FilmAppScreens.Top.name) { backStackEntry ->
            TopFilmsScreen(
                title = stringResource(FilmAppScreens.Top.title),
                filmsUiState = filmsViewModel.filmsUiState,
                retryAction = filmsViewModel::getFilmsPreviews,
                navController = navController,
                onFilmLongClick = filmsViewModel::addFavoriteFilm,
            )
        }
        composable("${FilmAppScreens.Details.name}/{filmId}") { backStackEntry ->
            val filmId = backStackEntry.arguments?.getString("filmId")?.toLongOrNull() ?: 0L
            FilmDetailsScreen(
                filmDetailsUiState = filmsViewModel.filmDetailsUiState,
                filmId = filmId,
                onBackClicked = { navController.popBackStack() },
                getFilmDetails = filmsViewModel::getFilmDetails,
            )
        }
        composable(FilmAppScreens.Favorite.name) {
            TopFilmsScreen(
                title = stringResource(FilmAppScreens.Favorite.title),
                filmsUiState = filmsViewModel.favoriteFilmsUiState,
                retryAction = filmsViewModel::getFavoriteFilms,
                navController = navController,
                onFilmLongClick = filmsViewModel::deleteFavoriteFilm,
            )
        }
    }
}
