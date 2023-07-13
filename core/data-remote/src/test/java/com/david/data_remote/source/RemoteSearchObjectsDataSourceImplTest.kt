package com.david.data_remote.source

import com.david.core.data_remote.networking.search.SearchApiModel
import com.david.core.data_remote.networking.search.SearchService
import com.david.core.data_remote.source.RemoteSearchObjectsDataSourceImpl
import com.david.domain.entity.SearchResult
import com.david.domain.entity.UseCaseException
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class RemoteSearchObjectsDataSourceImplTest {

    private val searchService = mockk<SearchService>()
    private val remoteSearchObjectsDataSource = RemoteSearchObjectsDataSourceImpl(
        searchService
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testSearchObjects() = runTest {
        val searchQuery = "sunflower"
        val remoteSearchObjects = SearchApiModel(10, listOf(1, 2, 3))
        val expectedSearchObjects = SearchResult(10, listOf(1, 2, 3))
        coEvery { searchService.searchObjects(searchQuery) } returns (remoteSearchObjects)
        val result = remoteSearchObjectsDataSource.searchObjects(searchQuery).first()
        Assert.assertEquals(expectedSearchObjects, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearchObjectsThrowError() = runTest {
        val searchQuery = "sunflower"
        coEvery { searchService.searchObjects(searchQuery) }.throws(RuntimeException())
        remoteSearchObjectsDataSource.searchObjects(searchQuery).catch {
            Assert.assertTrue(it is UseCaseException.UnknownException)
        }.collect()
    }
}