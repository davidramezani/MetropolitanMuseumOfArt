package com.david.metropolitanmuseumofart.presentation_search

sealed interface SearchResultUiState {
    object Loading : SearchResultUiState
    object EmptyQuery : SearchResultUiState
    data class LoadFailed(val errorMessage: String) : SearchResultUiState
    data class Success(
        val totalItems: Long,
        val items: List<Int>
    ) : SearchResultUiState
}