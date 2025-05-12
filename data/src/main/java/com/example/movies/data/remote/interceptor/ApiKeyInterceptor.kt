package com.example.movies.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request

private const val QUERY_PARAMETER = "apikey"
private const val API_KEY = "d146a04d" // FIXME

internal object ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain) = chain.proceed(chain.request().addApiKey())

    private fun Request.addApiKey() = newBuilder()
        .url(url.newBuilder().addQueryParameter(QUERY_PARAMETER, API_KEY).build())
        .build()
}