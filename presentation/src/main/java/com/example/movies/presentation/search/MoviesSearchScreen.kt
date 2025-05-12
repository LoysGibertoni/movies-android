package com.example.movies.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.example.movies.domain.model.Movie
import com.example.movies.presentation.widget.FullScreenError
import com.example.movies.presentation.widget.FullScreenErrorButton
import com.example.movies.presentation.widget.FullScreenLoadingIndicator
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesSearchScreen(
    onNavigateToMovieDetails: (id: String) -> Unit
) {
    val viewModel: MoviesSearchViewModel = koinViewModel()
    val query by viewModel.queryState.collectAsState()
    val movies = viewModel.moviesState.collectAsLazyPagingItems()

    Column {
        SearchBar(query) {
            viewModel.updateQuery(it)
        }
        MoviesList(movies) { movie ->
            onNavigateToMovieDetails(movie.id)
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .background(
            color = MaterialTheme.colorScheme.primary
        )) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .semantics { 
                    contentDescription = "Campo de texto para busca de filmes por título"
                },
            placeholder = { Text("Buscar por título") },
            value = query,
            onValueChange = onQueryChange,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Done
            )
        )
    }
}

@Composable
private fun MoviesList(
    movies: LazyPagingItems<Movie>,
    onItemClick: (Movie) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (movies.loadState.refresh) {
            LoadState.Loading -> FullScreenLoadingIndicator()
            is LoadState.Error -> FullScreenError(
                message = "Erro ao buscar filmes",
                button = FullScreenErrorButton(
                    text = "Tentar novamente",
                    onClick = movies::refresh
                )
            )
            is LoadState.NotLoading -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                if (movies.loadState.prepend is LoadState.Loading) {
                    item { ItemLoadingIndicator() }
                } else if (movies.loadState.prepend is LoadState.Error) {
                    item { ItemError(movies::retry) }
                }

                items(movies.itemCount) { position ->
                    val movie = movies[position]!!
                    MoviesListItem(movie) {
                        onItemClick(movie)
                    }
                }

                if (movies.loadState.append is LoadState.Loading) {
                    item { ItemLoadingIndicator() }
                } else if (movies.loadState.append is LoadState.Error) {
                    item { ItemError(movies::retry) }
                }
            }
        }
    }
}

@Composable
private fun MoviesListItem(
    movie: Movie,
    onClick: () -> Unit
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        leadingContent = {
            AsyncImage(
                modifier = Modifier.fillMaxWidth(fraction = 0.33f).aspectRatio(0.67f),
                model = movie.poster,
                contentDescription = "Imagem do poster do filme",
                placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
                error = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
            )
        },
        overlineContent = {
            Text(
                modifier = Modifier.semantics {
                    contentDescription = "Ano de lançamento do filme"
                },
                text = movie.year,
            )
        },
        headlineContent = {
            Text(
                modifier = Modifier.semantics {
                    contentDescription = "Título do filme"
                },
                text = movie.title,
            )
        },
        trailingContent = {
            Text(
                modifier = Modifier.semantics {
                    contentDescription = "Tipo do filme"
                },
                text = movie.type,
            )
        },
        shadowElevation = 8.dp
    )
}

@Composable
private fun ItemLoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxWidth().height(136.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ItemError(
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().height(136.dp).padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Filled.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Erro ao carregar dados",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.titleSmall,
        )
        ElevatedButton(
            modifier = Modifier.height(28.dp),
            onClick = onButtonClick,
        ) {
            Text(
                text = "Tentar novamente",
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MoviesSearchScreenPreview() {
    MoviesSearchScreen(
        onNavigateToMovieDetails = {}
    )
}