package com.example.movies.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
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

fun NavBackStackEntry.toRoute(): Route? =
    Route::class.sealedSubclasses.find { destination.hasRoute(it) }?.let(::toRoute)