package com.david.metropolitanmuseumofart.presentation_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.david.domain.usecase.GetObjectDetailUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val museumObjectConverter: MuseumObjectConverter,
    private val getObjectDetailUseCase: GetObjectDetailUseCase,
) : ViewModel() {

    private val objectId = savedStateHandle.get<Int>("objectId")!!
    private val _museumObjectFlow = MutableStateFlow<UiState<MuseumObjectModel>>(UiState.Loading)
    val museumObjectFlow: StateFlow<UiState<MuseumObjectModel>> = _museumObjectFlow

    init {
        getMuseumObject()
    }

    fun getMuseumObject() {
        viewModelScope.launch {
            getObjectDetailUseCase.execute(GetObjectDetailUseCase.Request(objectId)).map {
                museumObjectConverter.convert(it)
            }.collect {
                _museumObjectFlow.value = it
            }
        }
    }
}