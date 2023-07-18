package com.david.metropolitanmuseumofart.presentation_detail

import com.david.domain.entity.MuseumObject

sealed interface MuseumObjectUiState {
    object Loading : MuseumObjectUiState
    data class LoadFailed(val errorMessage : String) : MuseumObjectUiState
    data class Success(val museumObject: MuseumObject) : MuseumObjectUiState
}