package com.david.domain.usecase

import com.david.domain.entity.SearchResult
import com.david.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchObjectsUseCase @Inject constructor(
    configuration: Configuration,
    private val searchRepository: SearchRepository,
) : UseCase<SearchObjectsUseCase.Request, SearchObjectsUseCase.Response>(configuration) {

    override fun process(request: Request): Flow<Response> =
        searchRepository.searchObjectIDs(request.searchQuery)
            .map {
                Response(it)
            }

    data class Request(val searchQuery: String) : UseCase.Request
    data class Response(val searchResult: SearchResult) : UseCase.Response
}