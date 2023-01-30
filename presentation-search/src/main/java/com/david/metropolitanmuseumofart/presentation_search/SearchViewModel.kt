package com.david.metropolitanmuseumofart.presentation_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.domain.usecase.SearchObjectsUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchObjectsUseCase: SearchObjectsUseCase,
    private val searchedListConverter: SearchedListConverter
) : ViewModel() {

    private val minimumLengthToStartSearchProcess = 3
    private val _searchedListFlow = MutableStateFlow<UiState<SearchedListModel>>(UiState.Idle)
    val searchedListFlow: StateFlow<UiState<SearchedListModel>> = _searchedListFlow
    private val _searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            _searchQuery
                .filter {
                    it.length >= minimumLengthToStartSearchProcess
                }
                .debounce(500)
                .distinctUntilChanged()
                .collect { searchQuery ->
                    startNewSearch(searchQuery)
                }
        }
    }

    private fun startNewSearch(searchQuery: String) = viewModelScope.launch {
        _searchedListFlow.value = UiState.Loading
        searchObjectsUseCase.execute(SearchObjectsUseCase.Request(searchQuery))
            .map {
                searchedListConverter.convert(it)
            }.collect {
                _searchedListFlow.value = it
            }
    }

    fun onSearchQueryChange(searchQuery: String) {
        if (searchQuery.length >= minimumLengthToStartSearchProcess) {
            _searchQuery.value = searchQuery
        }
    }

}