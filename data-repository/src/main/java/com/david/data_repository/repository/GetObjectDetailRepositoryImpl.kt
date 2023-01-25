package com.david.data_repository.repository

import com.david.data_repository.data_source.local.LocalObjectDetailDataSource
import com.david.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import com.david.domain.repository.GetObjectDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetObjectDetailRepositoryImpl @Inject constructor(
    private val localObjectDetailDataSource: LocalObjectDetailDataSource,
    private val remoteObjectDetailDataSource: RemoteObjectDetailDataSource
) : GetObjectDetailRepository {

    override fun getObjectDetail(objectId: Int): Flow<MuseumObject> =
        localObjectDetailDataSource.getObjectDetail(objectId)
            .onStart {
                refreshUser(objectId)
            }

    private fun refreshUser(objectId: Int): Flow<MuseumObject> =
        remoteObjectDetailDataSource.getObjectDetail(objectId)
            .onEach {
                localObjectDetailDataSource.addObjectDetail(it)
            }
}