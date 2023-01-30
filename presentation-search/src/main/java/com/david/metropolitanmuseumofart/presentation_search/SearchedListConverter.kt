package com.david.metropolitanmuseumofart.presentation_search

import android.content.Context
import com.david.domain.usecase.SearchObjectsUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.CommonResultConverter
import com.david.metropolitanmuseumofart.presentation_search.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SearchedListConverter @Inject constructor(@ApplicationContext private val context: Context) : CommonResultConverter<SearchObjectsUseCase.Response, SearchedListModel>() {

    override fun convertSuccess(data: SearchObjectsUseCase.Response): SearchedListModel {
        return SearchedListModel(
            totalItems = context.getString(R.string.total_number_of_ids, data.searchResult.total.toString()),
            items = data.searchResult.objectIDs.map {
                SearchedListItemModel(it)
            }
        )
    }
}