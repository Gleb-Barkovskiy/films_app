package com.example.filmsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.filmsapp.R
import com.example.filmsapp.model.FilmPreview
import com.example.filmsapp.ui.FilmAppScreens
import com.example.filmsapp.ui.components.FilmsPreviewsList


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TopFilmsScreen(
    title: String,
    filmsUiState: FilmsUiState,
    retryAction: () -> Unit,
    navController: NavHostController,
    onFilmLongClick: (FilmPreview) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            if (softwareKeyboardController != null) {
                Toolbar(
                    title = title,
                    onSearchTextChanged = { searchText = it },
                    softwareKeyboardController = softwareKeyboardController
                )
            }
        },
        content = { innerPadding ->
            Column(modifier = modifier.padding(innerPadding)) {
                when (filmsUiState) {
                    is FilmsUiState.Success -> {
                        val filteredFilmPreviews = filmsUiState.filmPreviews.filter {
                            it.title.contains(searchText, ignoreCase = true)
                        }
                        FilmsPreviewsList(
                            filmsPreviews = filteredFilmPreviews,
                            onFilmClick = { filmId -> navController.navigate("${FilmAppScreens.Details.name}/$filmId") },
                            onFilmLongClick = {
                                onFilmLongClick(it)
                                navController.navigate(FilmAppScreens.Favorite.name)
                            }
                        )
                    }
                    is FilmsUiState.Loading -> LoadingScreen(modifier)
                    else -> ErrorScreen(retryAction, modifier = Modifier.fillMaxSize())
                }
            }
        },
        bottomBar = {
            BottomAppBar(
                contentColor = Color.Black,
                modifier = Modifier.background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = { navController.navigate(FilmAppScreens.Top.name) },
                        enabled = true
                    ) {
                        Icon(Icons.Filled.List, contentDescription = "Top")
                    }
                    IconButton(
                        onClick = { navController.navigate(FilmAppScreens.Favorite.name) },
                        enabled = true
                    ) {
                        Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favorite")
                    }
                }
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Toolbar(
    title: String,
    onSearchTextChanged: (String) -> Unit,
    softwareKeyboardController: SoftwareKeyboardController
) {
    val searchTextState = remember { mutableStateOf(TextFieldValue("")) }
    val isSearchVisible = remember { mutableStateOf(false) }
    var shouldCloseKeyboard by remember { mutableStateOf(true) }

    LaunchedEffect(isSearchVisible.value) {
        shouldCloseKeyboard = isSearchVisible.value && searchTextState.value.text.isEmpty()
    }

    TopAppBar(
        title = {
            if (isSearchVisible.value) {
                val focusRequester = remember { FocusRequester() }

                BasicTextField(
                    value = searchTextState.value,
                    onValueChange = {
                        searchTextState.value = it
                        onSearchTextChanged(it.text)
                    },
                    singleLine = true,
                    maxLines = 1,
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (searchTextState.value.text.isEmpty()) {
                                isSearchVisible.value = false
                                shouldCloseKeyboard = true
                            } else {
                                shouldCloseKeyboard = false
                            }
                            softwareKeyboardController.hide()
                        }
                    ),
                    decorationBox = { innerTextField ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.Search,
                                contentDescription = "Search",
                                modifier = Modifier.size(36.dp)
                            )
                            innerTextField()
                        }
                    },
                    textStyle = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                            if (it.isFocused && !shouldCloseKeyboard) {
                                softwareKeyboardController.show()
                            }
                        }
                )

                DisposableEffect(Unit) {
                    focusRequester.requestFocus()
                    onDispose { }
                }

                DisposableEffect(Unit) {
                    softwareKeyboardController.show()
                    onDispose { }
                }
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        actions = {
            IconButton(onClick = {
                isSearchVisible.value = !isSearchVisible.value
            }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    )
}


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}


@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}
