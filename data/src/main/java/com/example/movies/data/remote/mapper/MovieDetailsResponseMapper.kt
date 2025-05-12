package com.example.movies.data.remote.mapper

import com.example.movies.data.remote.response.MovieDetailsResponse
import com.example.movies.domain.model.MovieDetails

internal fun MovieDetailsResponse.toMovieDetails() = MovieDetails(
    id = imdbID,
    type = type,
    title = title,
    rated = rated,
    year = year,
    genre = genre,
    plot = plot,
    director = director,
    writer = writer,
    actors = actors,
    language = language,
    country = country,
    awards = awards,
    poster = poster,
    ratings = ratings.joinToString(System.lineSeparator()) { rating -> "${rating.source}: ${rating.value}" }
)