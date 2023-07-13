package com.david.core.data_repository.data_source.remote

import com.david.domain.entity.SearchResult
import kotlinx.coroutines.flow.Flow

interface RemoteSearchObjectsDataSource {

    fun searchObjects(searchQuery: String): Flow<SearchResult>
}