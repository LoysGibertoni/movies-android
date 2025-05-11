package com.example.movies.domain

import com.example.movies.domain.usecase.MovieDetailsUseCase
import com.example.movies.domain.usecase.SearchMoviesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val DomainModule = module {
    singleOf(::SearchMoviesUseCase)
    singleOf(::MovieDetailsUseCase)
}