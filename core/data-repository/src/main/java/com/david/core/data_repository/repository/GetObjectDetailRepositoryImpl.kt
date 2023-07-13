package com.david.core.data_repository.repository

import com.david.core.data_repository.data_source.local.LocalObjectDetailDataSource
import com.david.core.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import com.david.domain.repository.GetObjectDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class
GetObjectDetailRepositoryImpl @Inject constructor(
    private val localObjectDetailDataSource: LocalObjectDetailDataSource,
    private val remoteObjectDetailDataSource: RemoteObjectDetailDataSource
) : GetObjectDetailRepository {

    override fun getObjectDetail(objectId: Int): Flow<MuseumObject> {
        return localObjectDetailDataSource.getObjectDetail(objectId).onEach {
            if (it == null) {
                val museumObject = remoteObjectDetailDataSource.getObjectDetail(objectId)
                localObjectDetailDataSource.addObjectDetail(museumObject)
            }
        }.filterNotNull()
    }

}