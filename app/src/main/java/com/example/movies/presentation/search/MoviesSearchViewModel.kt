package com.example.movies.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.movies.domain.model.Movie
import com.example.movies.domain.usecase.SearchMoviesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

private const val QUERY_SAVED_SATATE_KEY = "query"
private val QUERY_DEBOUNCE_TIMEOUT = 500.milliseconds

@OptIn(FlowPreview::class)
class MoviesSearchViewModel(
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _moviesState: MutableStateFlow<PagingData<Movie>> = MutableStateFlow(PagingData.empty())
    val moviesState: StateFlow<PagingData<Movie>> = _moviesState

    val queryState: StateFlow<String> = savedStateHandle.getStateFlow(QUERY_SAVED_SATATE_KEY, "")

    init {
        viewModelScope.launch {
            queryState.debounce(QUERY_DEBOUNCE_TIMEOUT).collectLatest { query ->
                searchMoviesUseCase(query).cachedIn(viewModelScope).collect { moviesPagingData ->
                    _moviesState.value = moviesPagingData
                }
            }
        }
    }

    fun updateQuery(query: String) {
        savedStateHandle[QUERY_SAVED_SATATE_KEY] = query
    }
}
