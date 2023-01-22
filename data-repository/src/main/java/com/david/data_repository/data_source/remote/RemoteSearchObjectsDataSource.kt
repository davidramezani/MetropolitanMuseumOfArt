package com.david.data_repository.data_source.remote

import kotlinx.coroutines.flow.Flow

interface RemoteSearchObjectsDataSource {

    fun searchObjects(searchQuery: String): Flow<List<Int>>
}