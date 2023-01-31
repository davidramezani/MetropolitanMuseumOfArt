package com.david.metropolitanmuseumofart.presentation_detail

data class DetailModel(
    val objectID : Int,
    val primaryImage : String,
    val images : List<String>,
    val primaryImageSmall : String,
    val department : String,
    val objectName : String,
    val title : String,
    val medium : String,
    val artist : String,
    val artistBio : String
)