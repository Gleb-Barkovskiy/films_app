package com.example.filmsapp.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.filmsapp.R
import com.example.filmsapp.model.FilmPreview

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FilmsPreviewsList(
    filmsPreviews: List<FilmPreview>,
    onFilmClick: (Long) -> Unit,
    onFilmLongClick: (FilmPreview) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(filmsPreviews) {
            FilmPreviewCard(
                filmPreview = it,
                modifier = Modifier
                    .padding(8.dp)
                    .height(128.dp)
                    .combinedClickable(
                        onClick = { onFilmClick(it.filmId) },
                        onLongClick = { onFilmLongClick(it) },
                    )
            )
        }
    }
}

@Composable
fun FilmPreviewCard(
    filmPreview: FilmPreview,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(filmPreview.posterUrlPreview)
                    .crossfade(true)
                    .build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.film_preview_img),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            FilmPreviewInfo(
                title = filmPreview.title,
                year = filmPreview.year,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun FilmPreviewInfo(
    title: String,
    year: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = year,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}
