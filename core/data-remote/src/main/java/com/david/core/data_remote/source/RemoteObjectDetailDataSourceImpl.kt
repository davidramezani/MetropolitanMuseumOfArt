package com.david.core.data_remote.source

import com.david.core.data_remote.networking.detail.DetailService
import com.david.core.data_remote.networking.detail.MuseumObjectApiModel
import com.david.core.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import javax.inject.Inject

class RemoteObjectDetailDataSourceImpl @Inject constructor(
    private val detailService: DetailService
) : RemoteObjectDetailDataSource {

    override suspend fun getObjectDetail(objectId: Int): MuseumObject =
        convert(detailService.getObjectDetail(objectId))

    private fun convert(museumObjectApiModel: MuseumObjectApiModel) =
        museumObjectApiModel.run {
            MuseumObject(
                objectID = objectID,
                primaryImage = primaryImage,
                primaryImageSmall = primaryImageSmall,
                additionalImages = additionalImages,
                department = department,
                objectName = objectName,
                title = title,
                medium = medium,
                artist = artistDisplayName,
                artistBio = artistDisplayBio
            )
        }

}