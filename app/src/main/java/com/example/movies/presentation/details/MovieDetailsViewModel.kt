package com.example.movies.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.usecase.MovieDetailsUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MovieDetailsViewModel(
    private val id: String,
    private val movieDetailsUseCase: MovieDetailsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<MovieDetailsUiState> = MutableStateFlow(MovieDetailsUiState.Loading)
    val uiState: StateFlow<MovieDetailsUiState> = _uiState

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.value = MovieDetailsUiState.Loading
            try {
                _uiState.value = MovieDetailsUiState.Success(movieDetailsUseCase(id))
            } catch (_: Throwable) {
                _uiState.value = MovieDetailsUiState.Error
            }
        }
    }
}