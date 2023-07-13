package com.david.domain.usecase

import com.david.domain.entity.SearchResult
import com.david.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchObjectsUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {
    operator fun invoke(searchQuery: String) : Flow<SearchResult> =
        searchRepository.searchObjectIDs(searchQuery)
}