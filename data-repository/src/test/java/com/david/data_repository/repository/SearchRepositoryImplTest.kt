package com.david.data_repository.repository

import com.david.data_repository.data_source.remote.RemoteSearchObjectsDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchRepositoryImplTest {

    private val remoteSearchObjectsDataSource = mock<RemoteSearchObjectsDataSource>()
    private val searchRepository = SearchRepositoryImpl(
        remoteSearchObjectsDataSource
    )


    @ExperimentalCoroutinesApi
    @Test
    fun testSearchObjects() = runTest {
        val searchQuery = "sunflower"
        val objectIds = listOf(1, 2, 3)
        whenever(remoteSearchObjectsDataSource.searchObjects(searchQuery)).thenReturn(
            flowOf(
                objectIds
            )
        )
        val result = searchRepository.searchObjectIDs(searchQuery).first()
        Assert.assertEquals(objectIds, result)
    }
}