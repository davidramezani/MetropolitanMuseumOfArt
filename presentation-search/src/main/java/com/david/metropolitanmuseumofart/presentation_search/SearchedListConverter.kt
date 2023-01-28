package com.david.metropolitanmuseumofart.presentation_search

import com.david.domain.usecase.SearchObjectsUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.CommonResultConverter
import javax.inject.Inject

class SearchedListConverter @Inject constructor() : CommonResultConverter<SearchObjectsUseCase.Response, SearchedListModel>() {

    override fun convertSuccess(data: SearchObjectsUseCase.Response): SearchedListModel {
        return SearchedListModel(
            totalItems = data.searchResult.total,
            items = data.searchResult.objectIDs.map {
                SearchedListItemModel(it)
            }
        )
    }
}