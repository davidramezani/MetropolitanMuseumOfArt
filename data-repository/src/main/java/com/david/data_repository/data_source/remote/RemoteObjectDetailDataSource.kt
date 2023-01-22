package com.david.data_repository.data_source.remote

import com.david.domain.entity.MuseumObject
import kotlinx.coroutines.flow.Flow

interface RemoteObjectDetailDataSource {

    fun getObjectDetail(objectId: Int): Flow<MuseumObject>
}