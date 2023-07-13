package com.david.domain.entity

data class MuseumObject(
    val objectID : Int,
    val primaryImage : String,
    val primaryImageSmall : String,
    val additionalImages : List<String>,
    val department : String,
    val objectName : String,
    val title : String,
    val medium : String,
    val artist : String,
    val artistBio : String
)
