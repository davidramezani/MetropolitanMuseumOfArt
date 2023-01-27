package com.david.metropolitanmuseumofart.presentation_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.domain.usecase.SearchObjectsUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchObjectsUseCase: SearchObjectsUseCase,
    private val searchedListConverter: SearchedListConverter
) : ViewModel() {

    private val _searchedListFlow = MutableStateFlow<UiState<SearchedListModel>>(UiState.Idle)
    val searchedListFlow: StateFlow<UiState<SearchedListModel>> = _searchedListFlow

    private var searchJob: Job? = null
    private val searchQuery: String = ""

    fun search(searchQuery: String) {
        if (searchQuery.isNotEmpty()) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                _searchedListFlow.value = UiState.Loading
                searchObjectsUseCase.execute(SearchObjectsUseCase.Request(searchQuery))
                    .debounce(500)
                    .distinctUntilChanged()
                    .map {
                        searchedListConverter.convert(it)
                    }.collect {
                        _searchedListFlow.value = it
                    }
            }
        }
    }
}