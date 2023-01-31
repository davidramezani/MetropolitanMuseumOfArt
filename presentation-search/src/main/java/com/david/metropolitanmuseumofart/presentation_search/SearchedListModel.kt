package com.david.metropolitanmuseumofart.presentation_search

data class SearchedListModel(
    val totalItems : String,
    val items : List<SearchedListItemModel>
)

data class SearchedListItemModel(
    val id : Int
)