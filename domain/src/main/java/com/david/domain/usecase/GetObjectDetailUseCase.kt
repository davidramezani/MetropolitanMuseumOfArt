package com.david.domain.usecase

import com.david.domain.entity.MuseumObject
import com.david.domain.repository.DetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetObjectDetailUseCase @Inject constructor(
    configuration: UseCase.Configuration,
    private val detailRepository: DetailRepository
) : UseCase<GetObjectDetailUseCase.Request, GetObjectDetailUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        detailRepository.getObjectDetail(request.objectId)
            .map {
                Response(it)
            }

    data class Request(val objectId: Int) : UseCase.Request
    data class Response(val museumObject: MuseumObject) : UseCase.Response
}