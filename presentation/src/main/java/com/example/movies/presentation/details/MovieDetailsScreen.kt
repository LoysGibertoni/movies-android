package com.example.movies.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.movies.domain.model.MovieDetails
import com.example.movies.presentation.widget.FullScreenError
import com.example.movies.presentation.widget.FullScreenErrorButton
import com.example.movies.presentation.widget.FullScreenLoadingIndicator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesDetailsScreen(
    id: String,
    viewModel: MovieDetailsViewModel = koinViewModel {
        parametersOf(id)
    }
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        MovieDetailsUiState.Loading -> FullScreenLoadingIndicator()
        is MovieDetailsUiState.Success -> MovieDetailsContent((uiState as MovieDetailsUiState.Success).movieDetails)
        MovieDetailsUiState.Error -> FullScreenError(
            message = "Erro ao carregar dados do filme",
            button = FullScreenErrorButton(
                text = "Tentar novamente",
                onClick = viewModel::loadData
            )
        )
    }
}

@Composable
private fun MovieDetailsContent(movie: MovieDetails) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        ContentHeader(movie)
        ContentInfo(movie)
    }
}

@Composable
private fun ContentHeader(movie: MovieDetails) {
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.surfaceVariant),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxWidth(fraction = 0.33f).aspectRatio(0.67f),
                    model = movie.poster,
                    contentDescription = "Imagem do poster",
                    placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
                    error = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
                )

                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = "${movie.year} (${movie.type})",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = movie.genre,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Text(
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp),
                text = movie.rated,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun ContentInfo(movie: MovieDetails) {
    Text(
        modifier = Modifier.padding(16.dp),
        text = movie.plot,
        style = MaterialTheme.typography.bodyLarge
    )
    LabelValue(
        label = "Diretor",
        value = movie.director
    )
    LabelValue(
        label = "Escritor",
        value = movie.writer
    )
    LabelValue(
        label = "Atores",
        value = movie.actors
    )
    LabelValue(
        label = "Idioma",
        value = movie.language
    )
    LabelValue(
        label = "País",
        value = movie.country
    )
    LabelValue(
        label = "Prêmios",
        value = movie.awards
    )
    LabelValue(
        label = "Avaliações",
        value = movie.ratings
    )
}

@Composable
fun LabelValue(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.padding(
            vertical = 8.dp,
            horizontal = 16.dp
        )
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoviesDetailsScreenPreview() {
    MoviesDetailsScreen("id")
}