package com.example.movies.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.movies.data.remote.mapper.toMovie
import com.example.movies.data.remote.mapper.toMovieDetails
import com.example.movies.data.remote.source.MoviesApi
import com.example.movies.data.remote.source.MoviesSearchPagingSource
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDetails
import com.example.movies.domain.repository.MoviesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

private const val PAGE_SIZE = 30
private const val MAX_SIZE = PAGE_SIZE * 10

internal class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {

    override suspend fun search(title: String): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            maxSize = MAX_SIZE
        )
    ) {
        MoviesSearchPagingSource(title, api)
    }.flow.map { pagingData ->
        pagingData.map { it.toMovie() }
    }.flowOn(ioDispatcher)

    override suspend fun getDetails(id: String): MovieDetails = withContext(ioDispatcher) {
        api.getDetails(id).toMovieDetails()
    }
}