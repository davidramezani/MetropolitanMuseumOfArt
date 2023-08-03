package com.david.core.data_remote.source

import com.david.core.data_remote.networking.search.SearchApiModel
import com.david.core.data_remote.networking.search.SearchService
import com.david.core.data_repository.data_source.remote.RemoteSearchObjectsDataSource
import com.david.domain.entity.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteSearchObjectsDataSourceImpl @Inject constructor(
    private val searchService: SearchService
) : RemoteSearchObjectsDataSource {

    override fun searchObjects(searchQuery: String): Flow<SearchResult> = flow {
        emit(searchService.searchObjects(searchQuery))
    }.map { searchApiModel ->
        convert(searchApiModel)
    }

    private fun convert(searchApiModel: SearchApiModel) =
        SearchResult(searchApiModel.total, searchApiModel.objectIDs)
}