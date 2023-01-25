package com.david.data_repository.repository

import com.david.data_repository.data_source.remote.RemoteSearchObjectsDataSource
import com.david.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val remoteSearchObjectsDataSource: RemoteSearchObjectsDataSource
) : SearchRepository {

    override fun searchObjectIDs(searchQuery: String): Flow<List<Int>> =
        remoteSearchObjectsDataSource.searchObjects(searchQuery)
}