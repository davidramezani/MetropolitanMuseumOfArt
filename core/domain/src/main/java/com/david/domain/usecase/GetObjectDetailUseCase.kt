package com.david.domain.usecase

import com.david.domain.entity.MuseumObject
import com.david.domain.repository.GetObjectDetailRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetObjectDetailUseCase @Inject constructor(
    private val getObjectDetailRepository: GetObjectDetailRepository
) {
    operator fun invoke(objectId: Int): Flow<MuseumObject> =
        getObjectDetailRepository.getObjectDetail(objectId)
}