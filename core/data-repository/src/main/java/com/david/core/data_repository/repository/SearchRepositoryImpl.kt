package com.david.core.data_repository.repository

import com.david.core.data_repository.data_source.remote.RemoteSearchObjectsDataSource
import com.david.domain.entity.SearchResult
import com.david.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteSearchObjectsDataSource: RemoteSearchObjectsDataSource
) : SearchRepository {

    override fun searchObjectIDs(searchQuery: String): Flow<SearchResult> =
        remoteSearchObjectsDataSource.searchObjects(searchQuery)
}