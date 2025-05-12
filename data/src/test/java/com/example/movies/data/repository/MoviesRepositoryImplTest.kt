package com.example.movies.data.repository

import androidx.paging.LoadState
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.LoadErrorHandler
import androidx.paging.testing.asSnapshot
import com.example.movies.data.remote.mapper.toMovie
import com.example.movies.data.remote.response.MoviesSearchResponse
import com.example.movies.data.remote.source.MoviesApi
import com.example.movies.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MoviesRepositoryImplTest {


    private val api: MoviesApi = mockk()
    private val TestScope.repository: MoviesRepository
        get() = MoviesRepositoryImpl(api, StandardTestDispatcher(testScheduler))

    private fun createSearchResult(position: Int) = MoviesSearchResponse.Result(
        title = "title $position",
        year = "year $position",
        imdbID = "imdbID $position",
        type = "type $position",
        poster = "poster $position"
    )

    @Test
    fun `given non-empty api search results when search then result contains all api results`() = runTest {
        val title = "title"
        val firstPageItems: List<MoviesSearchResponse.Result> = (0..10).map(::createSearchResult)
        val secondPageItems: List<MoviesSearchResponse.Result> = (11..15).map(::createSearchResult)
        val expectedResult = (firstPageItems + secondPageItems).map { it.toMovie() }
        coEvery { api.search(title, 1) } returns mockk { every { results } returns firstPageItems }
        coEvery { api.search(title, 2) } returns mockk { every { results } returns secondPageItems }
        coEvery { api.search(title, 3) } returns mockk { every { results } returns emptyList() }

        val result = repository.search(title).asSnapshot()

        assertEquals(expectedResult, result)
    }

    @Test
    fun `given empty api search results when search then result is empty`() = runTest {
        val title = "title"
        coEvery { api.search(title, 1) } returns mockk { every { results } returns emptyList() }

        val result = repository.search(title).asSnapshot()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `given api search error when search then result equals to api error`() = runTest {
        val title = "title"
        val error: Throwable = mockk()
        coEvery { api.search(title, 1) } throws error

        testScheduler.advanceUntilIdle()

        var result: Throwable? = null
        repository.search(title).asSnapshot(
            onError = LoadErrorHandler {
                result = (it.refresh as? LoadState.Error)?.error
                ErrorRecovery.RETURN_CURRENT_SNAPSHOT
            }
        )

        assertEquals(error, result)
    }
}