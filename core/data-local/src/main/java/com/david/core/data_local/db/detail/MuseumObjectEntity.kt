package com.david.core.data_local.db.detail

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.david.domain.entity.MuseumObject

@Entity(tableName = "museum_object")
data class MuseumObjectEntity(
    @PrimaryKey
    val objectID: Int,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val department: String,
    val objectName: String,
    val title: String,
    val medium: String,
    val artist: String,
    val artistBio: String
)

fun MuseumObjectEntity.asExternalModel() = MuseumObject(
    objectID = objectID,
    primaryImage = primaryImage,
    primaryImageSmall = primaryImageSmall,
    additionalImages = additionalImages,
    department = department,
    objectName = objectName,
    title = title,
    medium = medium,
    artist = artist,
    artistBio = artistBio
)

fun MuseumObject.asEntity() = MuseumObjectEntity(
    objectID = objectID,
    primaryImage = primaryImage,
    primaryImageSmall = primaryImageSmall,
    additionalImages = additionalImages,
    department = department,
    objectName = objectName,
    title = title,
    medium = medium,
    artist = artist,
    artistBio = artistBio
)