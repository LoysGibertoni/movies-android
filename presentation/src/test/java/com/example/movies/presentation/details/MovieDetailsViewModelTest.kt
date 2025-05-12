package com.example.movies.presentation.details

import com.example.movies.domain.model.MovieDetails
import com.example.movies.domain.usecase.MovieDetailsUseCase
import com.example.movies.presentation.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailsViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val id = "id"
    private val useCase: MovieDetailsUseCase = mockk()
    private val viewModel by lazy {
        MovieDetailsViewModel(id, useCase)
    }

    @Test
    fun `given load success when view model is initialized then UI state starts with Loading and is updated to Success`() = runTest {
        val movieDetails: MovieDetails = mockk()
        coEvery { useCase(id) } returns  movieDetails

        assertEquals(MovieDetailsUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()
        assertEquals(MovieDetailsUiState.Success(movieDetails), viewModel.uiState.value)
    }

    @Test
    fun `given load error when view model is initialized then UI state starts with Loading and is updated to Error`() = runTest {
        val error: Throwable = mockk()
        coEvery { useCase(id) } throws error

        assertEquals(MovieDetailsUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()
        assertEquals(MovieDetailsUiState.Error, viewModel.uiState.value)
    }

    @Test
    fun `given load success when load data then UI state starts with Loading and is updated to Success`() = runTest {
        val movieDetails: MovieDetails = mockk()
        coEvery { useCase(id) } returns movieDetails

        viewModel.loadData()

        assertEquals(MovieDetailsUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()
        assertEquals(MovieDetailsUiState.Success(movieDetails), viewModel.uiState.value)
    }

    @Test
    fun `given load error when load data then UI state starts with Loading and is updated to Error`() = runTest {
        val error: Throwable = mockk()
        coEvery { useCase(id) } throws error

        viewModel.loadData()

        assertEquals(MovieDetailsUiState.Loading, viewModel.uiState.value)
        advanceUntilIdle()
        assertEquals(MovieDetailsUiState.Error, viewModel.uiState.value)
    }
}