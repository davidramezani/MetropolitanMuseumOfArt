package com.david.data_repository.data_source.local

import com.david.domain.entity.MuseumObject
import kotlinx.coroutines.flow.Flow

interface LocalObjectDetailDataSource {

    fun getObjectDetail(objectId: Int): Flow<MuseumObject>

    suspend fun addObjectDetail(museumObject: MuseumObject)
}