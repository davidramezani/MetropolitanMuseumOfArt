package com.david.data_local.source

import com.david.data_local.db.detail.DetailDao
import com.david.data_local.db.detail.MuseumObjectEntity
import com.david.data_repository.data_source.local.LocalObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalObjectDetailDataSourceImpl @Inject constructor(
    private val detailDao: DetailDao
) : LocalObjectDetailDataSource {
    override fun getObjectDetail(objectId: Int): Flow<MuseumObject> =
        detailDao.getObjectDetail(objectId).map {
            MuseumObject(
                objectID = it.objectID,
                primaryImage = it.primaryImage,
                primaryImageSmall = it.primaryImageSmall,
                additionalImages = it.additionalImages,
                department = it.department,
                objectName = it.objectName,
                title = it.title,
                medium = it.medium,
                artist = it.artist,
                artistBio = it.artistBio
            )
        }

    override suspend fun addObjectDetail(museumObject: MuseumObject) = detailDao.insertObjectDetail(
        MuseumObjectEntity(
            objectID = museumObject.objectID,
            primaryImage = museumObject.primaryImage,
            primaryImageSmall = museumObject.primaryImageSmall,
            additionalImages = museumObject.additionalImages,
            department = museumObject.department,
            objectName = museumObject.objectName,
            title = museumObject.title,
            medium = museumObject.medium,
            artist = museumObject.artist,
            artistBio = museumObject.artistBio
        )
    )
}