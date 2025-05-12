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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import com.example.movies.domain.model.MovieDetails
import com.example.movies.presentation.R
import com.example.movies.presentation.widget.FullScreenError
import com.example.movies.presentation.widget.FullScreenErrorButton
import com.example.movies.presentation.widget.FullScreenLoadingIndicator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoviesDetailsScreen(id: String) {
    val viewModel: MovieDetailsViewModel = koinViewModel {
        parametersOf(id)
    }
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        MovieDetailsUiState.Loading -> FullScreenLoadingIndicator()
        is MovieDetailsUiState.Success -> MovieDetailsContent((uiState as MovieDetailsUiState.Success).movieDetails)
        MovieDetailsUiState.Error -> FullScreenError(
            message = stringResource(R.string.details_error_message),
            button = FullScreenErrorButton(
                text = stringResource(R.string.try_again_button),
                onClick = viewModel::loadData
            )
        )
    }
}

@Composable
private fun MovieDetailsContent(movie: MovieDetails) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        ContentHeader(movie)
        ContentInfo(movie)
    }
}

@Composable
private fun ContentHeader(movie: MovieDetails) {
    val context = LocalContext.current
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
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.33f)
                        .aspectRatio(0.67f),
                    model = movie.poster,
                    contentDescription = stringResource(R.string.poster_content_description),
                    placeholder = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
                    error = ColorPainter(MaterialTheme.colorScheme.surfaceDim),
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.space_medium))
                ) {
                    Text(
                        modifier = Modifier.semantics {
                            contentDescription = context.getString(R.string.year_and_type_content_description)
                        },
                        text = "${movie.year} (${movie.type})",
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        modifier = Modifier
                            .padding(vertical = dimensionResource(R.dimen.space_tiny))
                            .semantics {
                                contentDescription =
                                    context.getString(R.string.title_content_description)
                            },
                        text = movie.title,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        modifier = Modifier.semantics {
                            contentDescription = context.getString(R.string.genre_content_description)
                        },
                        text = movie.genre,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(dimensionResource(R.dimen.space_medium))
                    .semantics {
                        contentDescription = context.getString(R.string.rated_content_description)
                    },
                text = movie.rated,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun ContentInfo(movie: MovieDetails) {
    val context = LocalContext.current
    Text(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.space_medium))
            .semantics {
                contentDescription = context.getString(R.string.plot_content_description)
            },
        text = movie.plot,
        style = MaterialTheme.typography.bodyLarge
    )
    LabelValue(
        label = stringResource(R.string.director_label),
        value = movie.director
    )
    LabelValue(
        label = stringResource(R.string.writer_label),
        value = movie.writer
    )
    LabelValue(
        label = stringResource(R.string.actors_label),
        value = movie.actors
    )
    LabelValue(
        label = stringResource(R.string.language_label),
        value = movie.language
    )
    LabelValue(
        label = stringResource(R.string.country_label),
        value = movie.country
    )
    LabelValue(
        label = stringResource(R.string.awards_label),
        value = movie.awards
    )
    LabelValue(
        label = stringResource(R.string.ratings_label),
        value = movie.ratings
    )
}

@Composable
private fun LabelValue(
    label: String,
    value: String
) {
    Column(
        modifier = Modifier.padding(
            vertical = dimensionResource(R.dimen.space_small),
            horizontal = dimensionResource(R.dimen.space_medium)
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
private fun MoviesDetailsScreenPreview() {
    MoviesDetailsScreen("id")
}