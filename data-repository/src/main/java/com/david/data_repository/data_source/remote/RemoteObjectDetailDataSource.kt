package com.david.data_repository.data_source.remote

import com.david.domain.entity.MuseumObject
import kotlinx.coroutines.flow.Flow

interface RemoteObjectDetailDataSource {

   suspend fun getObjectDetail(objectId: Int): MuseumObject
}