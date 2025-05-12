package com.example.movies.data.remote.interceptor

import Movies.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Request

private const val QUERY_PARAMETER = "apikey"

internal object ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request().addApiKey())

    private fun Request.addApiKey() = newBuilder()
        .url(url.newBuilder().addQueryParameter(QUERY_PARAMETER, BuildConfig.API_KEY).build())
        .build()
}