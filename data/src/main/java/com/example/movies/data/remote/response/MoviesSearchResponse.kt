package com.example.movies.data.remote.response

import com.google.gson.annotations.SerializedName


internal data class MoviesSearchResponse(
    @SerializedName("Search") val results: List<Result>?,
    @SerializedName("totalResults") val totalResults: String,
    @SerializedName("Response") val response: Boolean
) {

    data class Result(
        @SerializedName("Title") val title: String,
        @SerializedName("Year") val year: String,
        @SerializedName("imdbID") val imdbID: String,
        @SerializedName("Type") val type: String,
        @SerializedName("Poster") val poster: String
    )
}