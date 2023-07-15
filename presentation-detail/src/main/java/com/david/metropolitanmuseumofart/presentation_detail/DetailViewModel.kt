package com.david.metropolitanmuseumofart.presentation_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.core.common.result.Result
import com.david.core.common.result.asResult
import com.david.domain.usecase.GetObjectDetailUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.UiSingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getObjectDetailUseCase: GetObjectDetailUseCase,
) : ViewModel() {

    private val objectId = savedStateHandle.get<Int>("objectId")!!
    private val _museumObjectUiState =
        MutableStateFlow<MuseumObjectUiState>(MuseumObjectUiState.Loading)
    val museumObjectUiState: StateFlow<MuseumObjectUiState> = _museumObjectUiState
    private val _singleEventFlow = Channel<UiSingleEvent>()
    val singleEventFlow = _singleEventFlow.receiveAsFlow()

    init {
        getMuseumObject()
    }

    fun getMuseumObject() {
        viewModelScope.launch {
            getObjectDetailUseCase(objectId).collectLatest {
                _museumObjectUiState.value = MuseumObjectUiState.Success(it)
            }
        }
        /*getObjectDetailUseCase(objectId).asResult().map {
            when (it) {
                is Result.Success -> {
                    _museumObjectUiState.value = MuseumObjectUiState.Success(it.data)
                }

                is Result.Error -> {
                    _museumObjectUiState.value =
                        MuseumObjectUiState.LoadFailed(it.exception?.message ?: "")
                }

                Result.Loading -> {
                    _museumObjectUiState.value = MuseumObjectUiState.Loading
                }
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            MuseumObjectUiState.Loading
        )*/
    }

    fun backIconClicked() {
        viewModelScope.launch {
            _singleEventFlow.send(DetailSingleEvent.CloseDetailFragment)
        }
    }
}