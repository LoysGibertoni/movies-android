package com.example.movies.data.remote.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movies.data.remote.response.MoviesSearchResponse

private const val INITIAL_PAGE = 1

internal class MoviesSearchPagingSource(
    private val title: String,
    private val api: MoviesApi
) : PagingSource<Int, MoviesSearchResponse.Result>() {

    override fun getRefreshKey(state: PagingState<Int, MoviesSearchResponse.Result>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MoviesSearchResponse.Result> {
        try {
            val page = params.key ?: INITIAL_PAGE
            val response = api.search(title, page)
            val results = response.results.orEmpty()
            return LoadResult.Page(
                data = results,
                prevKey = page.dec().takeIf { it >= INITIAL_PAGE },
                nextKey = page.inc().takeIf { results.isNotEmpty() }
            )
        } catch (error: Throwable) {
            return LoadResult.Error(error)
        }
    }
}