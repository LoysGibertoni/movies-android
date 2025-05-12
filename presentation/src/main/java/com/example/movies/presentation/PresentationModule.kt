package com.example.movies.presentation

import com.example.movies.presentation.details.MovieDetailsViewModel
import com.example.movies.presentation.search.MoviesSearchViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val PresentationModule = module {
    viewModelOf(::MoviesSearchViewModel)
    viewModelOf(::MovieDetailsViewModel)
}