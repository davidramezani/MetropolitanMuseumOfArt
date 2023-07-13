package com.david.core.data_remote.networking.detail

import kotlinx.serialization.Serializable

@Serializable
data class MuseumObjectApiModel(
    val objectID : Int,
    val primaryImage : String,
    val primaryImageSmall : String,
    val additionalImages : List<String>,
    val department : String,
    val objectName : String,
    val title : String,
    val medium : String,
    val artistDisplayName : String,
    val artistDisplayBio : String
)
