package com.david.core.data_repository.data_source.remote

import com.david.domain.entity.MuseumObject

interface RemoteObjectDetailDataSource {

    suspend fun getObjectDetail(objectId: Int): MuseumObject
}