package com.david.data_remote.networking.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchApiModel(
    val total : Long,
    val objectIDs : List<Int>
)
