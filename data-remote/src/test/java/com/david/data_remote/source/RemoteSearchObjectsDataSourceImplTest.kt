package com.david.data_remote.source

import com.david.data_remote.networking.search.SearchApiModel
import com.david.data_remote.networking.search.SearchService
import com.david.domain.entity.UseCaseException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RemoteSearchObjectsDataSourceImplTest {

    private val searchService = mock<SearchService>()
    private val remoteSearchObjectsDataSource = RemoteSearchObjectsDataSourceImpl(
        searchService
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testSearchObjects() = runTest {
        val searchQuery = "sunflower"
        val remoteSearchObjects = SearchApiModel(listOf(1, 2, 3))
        val expectedSearchObjects = listOf(1, 2, 3)
        whenever(searchService.searchObjects(searchQuery)).thenReturn(remoteSearchObjects)
        val result = remoteSearchObjectsDataSource.searchObjects(searchQuery).first()
        Assert.assertEquals(expectedSearchObjects, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearchObjectsThrowError() = runTest {
        val searchQuery = "sunflower"
        whenever(searchService.searchObjects(searchQuery)).thenThrow(RuntimeException())
        remoteSearchObjectsDataSource.searchObjects(searchQuery).catch {
            Assert.assertTrue(it is UseCaseException.UnknownException)
        }.collect()
    }
}