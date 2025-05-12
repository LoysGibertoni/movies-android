package com.example.movies.domain.usecase

import com.example.movies.domain.model.MovieDetails
import com.example.movies.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class MovieDetailsUseCaseTest {

    private val repository: MoviesRepository = mockk()
    private val useCase = MovieDetailsUseCase(repository)

    @Test
    fun `given repository successfully gets details when invoke use case then use case result equals to repository result`() = runTest {
        val id = "id"
        val movieDetails: MovieDetails = mockk()
        coEvery { repository.getDetails(id) } returns movieDetails

        val result = useCase(id)

        assertEquals(movieDetails, result)
    }

    @Test
    fun `given repository fails to get details when invoke use case then use case result equals to repository error`() = runTest {
        val id = "id"
        val error: Throwable = mockk()
        coEvery { repository.getDetails(id) } throws error

        val result = runCatching { useCase(id) }.exceptionOrNull()

        assertEquals(error, result)
    }
}