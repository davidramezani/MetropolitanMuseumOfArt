package com.david.data_remote.source

import com.david.data_remote.networking.detail.DetailService
import com.david.data_remote.networking.detail.MuseumObjectApiModel
import com.david.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import com.david.domain.entity.UseCaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteObjectDetailDataSourceImpl @Inject constructor(
    private val detailService: DetailService
) : RemoteObjectDetailDataSource {

    override fun getObjectDetail(objectId: Int): Flow<MuseumObject> = flow {
        emit(detailService.getObjectDetail(objectId))
    }.map { museumObjectApiModel ->
        convert(museumObjectApiModel)
    }.catch {
        throw UseCaseException.UnknownException(it)
    }

    private fun convert(museumObjectApiModel: MuseumObjectApiModel) =
        MuseumObject(
            objectID = museumObjectApiModel.objectID,
            primaryImage = museumObjectApiModel.primaryImage,
            primaryImageSmall = museumObjectApiModel.primaryImageSmall,
            additionalImages = museumObjectApiModel.additionalImages,
            department = museumObjectApiModel.department,
            objectName = museumObjectApiModel.objectName
        )

}