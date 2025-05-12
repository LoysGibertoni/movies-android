package com.example.movies.data

import Movies.data.BuildConfig
import com.example.movies.data.remote.interceptor.ApiKeyInterceptor
import com.example.movies.data.remote.source.MoviesApi
import com.example.movies.data.repository.MoviesRepositoryImpl
import com.example.movies.domain.repository.MoviesRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val DataModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(ApiKeyInterceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .baseUrl(BuildConfig.API_URL)
            .build()
    }

    single<MoviesApi> {
        get<Retrofit>().create()
    }

    single<MoviesRepository> {
        MoviesRepositoryImpl(api = get())
    }
}