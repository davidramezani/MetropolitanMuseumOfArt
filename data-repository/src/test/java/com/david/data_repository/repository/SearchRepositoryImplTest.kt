package com.david.data_repository.repository

import com.david.data_repository.data_source.remote.RemoteSearchObjectsDataSource
import com.david.domain.entity.SearchResult
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class SearchRepositoryImplTest {

    private val remoteSearchObjectsDataSource = mockk<RemoteSearchObjectsDataSource>()
    private val searchRepository = SearchRepositoryImpl(
        remoteSearchObjectsDataSource
    )


    @ExperimentalCoroutinesApi
    @Test
    fun testSearchObjects() = runTest {
        val searchQuery = "sunflower"
        val objectIds = SearchResult(3, listOf(1, 2, 3))
        every { remoteSearchObjectsDataSource.searchObjects(searchQuery) }.returns(
            flowOf(
                objectIds
            )
        )
        val result = searchRepository.searchObjectIDs(searchQuery).first()
        Assert.assertEquals(objectIds, result)
    }
}