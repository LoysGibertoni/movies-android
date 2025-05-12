package com.example.movies.domain.repository

import androidx.paging.PagingData
import com.example.movies.domain.model.Movie
import com.example.movies.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun search(title: String): Flow<PagingData<Movie>>
    suspend fun getDetails(id: String): MovieDetails
}