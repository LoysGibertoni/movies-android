package com.example.movies.mock

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module


class MockInterceptor(
    private val mocks: List<Mock>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return request.answerWith(
            mocks.find { mock ->
                mock.predicate(request)
            }?.response ?: Mock.Response("")
        )
    }

    data class Mock(
        val predicate: (Request) -> Boolean,
        val response: Mock.Response
    ) {

        data class Response(
            val body: String,
            val code: Int = 200
        )
    }
}

private fun Request.answerWith(response: MockInterceptor.Mock.Response) = Response.Builder()
    .request(this)
    .body(response.body.toResponseBody("application/json".toMediaType()))
    .code(response.code)
    .protocol(Protocol.HTTP_1_1)
    .message("")
    .build()

fun startMockServer(mocks: List<MockInterceptor.Mock>) {
    loadKoinModules(
        module {
            single {
                OkHttpClient.Builder()
                    .addInterceptor(MockInterceptor(mocks))
                    .build()
            }
        }
    )
}