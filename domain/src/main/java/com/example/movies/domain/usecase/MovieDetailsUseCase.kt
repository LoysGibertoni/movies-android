package com.example.movies.domain.usecase

import com.example.movies.domain.repository.MoviesRepository

class MovieDetailsUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(id: String) = repository.getDetails(id)
}