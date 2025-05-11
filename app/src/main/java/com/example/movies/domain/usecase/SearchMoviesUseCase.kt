package com.example.movies.domain.usecase

import androidx.paging.PagingData
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private const val MIN_SEARCH_LENGTH = 3

class SearchMoviesUseCase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(title: String): Flow<PagingData<Movie>> =
        if (title.length < MIN_SEARCH_LENGTH) flowOf(PagingData.Companion.empty())
        else repository.search(title)
}