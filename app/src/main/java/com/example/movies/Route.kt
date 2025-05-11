package com.example.movies

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(
    val title: String
) {

    @Serializable
    data object MoviesSearch : Route(
        title = "Busca"
    )

    @Serializable
    data class MovieDetails(
        val id: String
    ) : Route(
        title = "Detalhes"
    )
}