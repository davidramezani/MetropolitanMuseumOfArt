package com.david.domain.repository

import com.david.domain.entity.MuseumObject
import kotlinx.coroutines.flow.Flow

interface GetObjectDetailRepository {
    fun getObjectDetail(objectId : Int) : Flow<MuseumObject>
}