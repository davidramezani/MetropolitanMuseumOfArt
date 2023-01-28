package com.david.metropolitanmuseumofart.presentation_search

data class SearchedListModel(
    val totalItems : Long,
    val items : List<SearchedListItemModel>
)

data class SearchedListItemModel(
    val id : Int
)