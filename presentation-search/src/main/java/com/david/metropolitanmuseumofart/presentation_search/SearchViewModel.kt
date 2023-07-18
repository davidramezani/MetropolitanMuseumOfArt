package com.david.metropolitanmuseumofart.presentation_search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.core.common.result.Result
import com.david.core.common.result.asResult
import com.david.core.common.result.getMessage
import com.david.domain.usecase.GetSearchObjectsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchObjectsUseCase: GetSearchObjectsUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY, "")

    @OptIn(FlowPreview::class)
    val searchResultUiState: StateFlow<SearchResultUiState> =
        searchQuery.debounce(500).flatMapLatest { query ->
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
                            SearchResultUiState.LoadFailed(
                                it.exception?.getMessage()
                                    ?: com.david.core.common.R.string.unknown_error
                            )
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

    fun retrySearch() = viewModelScope.launch {
        val lastSearchQuery = savedStateHandle.get<String>(SEARCH_QUERY)
        savedStateHandle[SEARCH_QUERY] = ""
        delay(100)
        savedStateHandle[SEARCH_QUERY] = lastSearchQuery
    }
}

private const val SEARCH_QUERY = "searchQuery"
private const val SEARCH_QUERY_MIN_LENGTH = 3