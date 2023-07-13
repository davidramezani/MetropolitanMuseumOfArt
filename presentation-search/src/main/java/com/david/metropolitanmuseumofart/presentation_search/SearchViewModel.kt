package com.david.metropolitanmuseumofart.presentation_search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.core.common.result.Result
import com.david.core.common.result.asResult
import com.david.domain.usecase.GetSearchObjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchObjectsUseCase: GetSearchObjectsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    val searchResultUiState: StateFlow<SearchResultUiState> = searchQuery.flatMapLatest { query ->
        if (query.length < SEARCH_QUERY_MIN_LENGTH) {
            flowOf(SearchResultUiState.EmptyQuery)
        } else {
            getSearchObjectsUseCase(query).asResult().map {
                when (it) {
                    is Result.Success -> {
                        SearchResultUiState.Success(
                            totalItems = it.data.total,
                            items = it.data.objectIDs
                        )
                    }

                    is Result.Loading -> {
                        SearchResultUiState.Loading
                    }

                    is Result.Error -> {
                        SearchResultUiState.LoadFailed(it.exception?.message ?: "")
                    }
                }
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SearchResultUiState.EmptyQuery
    )

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY] = query
    }
}

private const val SEARCH_QUERY = "searchQuery"
private const val SEARCH_QUERY_MIN_LENGTH = 3