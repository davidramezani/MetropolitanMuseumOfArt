package com.david.data_remote.source

import com.david.data_remote.networking.detail.DetailService
import com.david.data_remote.networking.detail.MuseumObjectApiModel
import com.david.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import com.david.domain.entity.UseCaseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteObjectDetailDataSourceImpl @Inject constructor(
    private val detailService: DetailService
) : RemoteObjectDetailDataSource {

    override suspend fun getObjectDetail(objectId: Int): MuseumObject = withContext(Dispatchers.IO){
        convert(detailService.getObjectDetail(objectId))
    }
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