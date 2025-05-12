package com.example.movies.presentation.details

import com.example.movies.domain.model.MovieDetails

sealed interface MovieDetailsUiState {
    data object Loading : MovieDetailsUiState
    data class Success(val movieDetails: MovieDetails) : MovieDetailsUiState
    data object Error : MovieDetailsUiState
}
