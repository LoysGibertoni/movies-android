package com.example.movies.domain.usecase

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.movies.domain.model.Movie
import com.example.movies.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SearchMoviesUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val useCase = SearchMoviesUseCase(repository)

    @Test
    fun `given empty title when invoke use case then result is empty`() = runTest {
        val title = ""
        coEvery { repository.search(title) } returns flowOf(mockk())

        val result = useCase(title).asSnapshot()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `given title with less than 3 characters when invoke use case then result is empty`() = runTest {
        val title = "12"
        coEvery { repository.search(title) } returns flowOf(mockk())

        val result = useCase(title).asSnapshot()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `given valid title when invoke use case then result is equal to repository result`() = runTest {
        val title = "123"
        val movies: List<Movie> = (0..10).map { mockk() }
        coEvery { repository.search(title) } returns flowOf(PagingData.from(movies))

        val result = useCase(title).asSnapshot()

        assertEquals(movies, result)
    }

    @Test
    fun `given repository fails to search movies when invoke use case then use case result equals to repository error`() = runTest {
        val title = "title"
        val error: Throwable = mockk()
        coEvery { repository.search(title) } throws error

        val result = runCatching { useCase(title) }.exceptionOrNull()

        assertEquals(error, result)
    }
}