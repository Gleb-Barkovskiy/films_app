package com.example.filmsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.filmsapp.R
import kotlin.reflect.KFunction1

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmDetailsScreen(
    filmId: Long,
    filmDetailsUiState: FilmDetailsUiState,
    getFilmDetails: KFunction1<Long, Unit>,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(filmId) {
        getFilmDetails(filmId)
    }

    when (filmDetailsUiState) {
        is FilmDetailsUiState.Success -> {
            val filmDetails = filmDetailsUiState.filmDetails
            val genres = filmDetails.genres
            val countries = filmDetails.countries

            Column(
                modifier = modifier.verticalScroll(rememberScrollState())
            ) {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = { onBackClicked() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    modifier = Modifier
                        .background(Color.Transparent)
                )

                Image(
                    painter = rememberAsyncImagePainter(filmDetails.posterUrl),
                    contentScale = ContentScale.Fit,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp)
                )

                Text(
                    text = filmDetails.title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )

                Text(
                    text = filmDetails.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "${stringResource(R.string.genres)} ${genres.joinToString(", ") { it.genre }}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )

                Text(
                    text = "${stringResource(R.string.countries)} ${countries.joinToString(", ") { it.country }}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        is FilmDetailsUiState.Error -> {
            Text(
                text = stringResource(R.string.details_fetch_failed),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
        is FilmDetailsUiState.Loading -> {
            LoadingScreen(modifier)
        }
    }
}

