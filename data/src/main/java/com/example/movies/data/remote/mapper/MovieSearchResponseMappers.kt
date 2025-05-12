package com.example.movies.data.remote.mapper

import com.example.movies.data.remote.response.MoviesSearchResponse
import com.example.movies.domain.model.Movie

internal fun MoviesSearchResponse.Result.toMovie() = Movie(
    id = imdbID,
    title = title,
    year = year,
    type = type,
    poster = poster
)