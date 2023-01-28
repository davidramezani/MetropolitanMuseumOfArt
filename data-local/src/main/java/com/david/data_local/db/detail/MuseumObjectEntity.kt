package com.david.data_local.db.detail

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "museum_object")
data class MuseumObjectEntity(
    @PrimaryKey
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