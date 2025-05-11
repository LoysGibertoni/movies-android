package com.example.movies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.movies.presentation.details.MoviesDetailsScreen
import com.example.movies.presentation.search.MoviesSearchScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Route.MoviesSearch
    ) {
        composable<Route.MoviesSearch> {
            MoviesSearchScreen(
                onNavigateToMovieDetails = { id ->
                    navController.navigate(Route.MovieDetails(id))
                }
            )
        }
        composable<Route.MovieDetails> { backStackEntry ->
            val route: Route.MovieDetails = backStackEntry.toRoute()
            MoviesDetailsScreen(route.id)
        }
    }
}