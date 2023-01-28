package com.david.data_repository.repository

import com.david.data_repository.data_source.local.LocalObjectDetailDataSource
import com.david.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import com.david.domain.repository.GetObjectDetailRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetObjectDetailRepositoryImpl @Inject constructor(
    private val localObjectDetailDataSource: LocalObjectDetailDataSource,
    private val remoteObjectDetailDataSource: RemoteObjectDetailDataSource
) : GetObjectDetailRepository {

    override fun getObjectDetail(objectId: Int): Flow<MuseumObject> {
        val result = localObjectDetailDataSource.getObjectDetail(objectId)

        return remoteObjectDetailDataSource.getObjectDetail(objectId)
            .onStart {
                result.first()?.let {
                    emit(it)
                }
            }
            .onEach {
                localObjectDetailDataSource.addObjectDetail(it)
            }
    }
}