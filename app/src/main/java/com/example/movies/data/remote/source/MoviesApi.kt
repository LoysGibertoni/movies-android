package com.example.movies.data.remote.source

import com.example.movies.data.remote.response.MovieDetailsResponse
import com.example.movies.data.remote.response.MoviesSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("/")
    suspend fun search(
        @Query(Parameters.TITLE) title: String,
        @Query(Parameters.PAGE) page: Int
    ): MoviesSearchResponse

    @GET("/")
    suspend fun getDetails(@Query(Parameters.ID) id: String): MovieDetailsResponse

    private object Parameters {
        const val TITLE = "s"
        const val PAGE = "page"
        const val ID = "i"
    }
}