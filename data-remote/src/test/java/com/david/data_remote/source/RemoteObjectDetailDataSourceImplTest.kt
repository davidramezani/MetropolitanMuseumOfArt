package com.david.data_remote.source

import com.david.data_remote.networking.detail.DetailService
import com.david.data_remote.networking.detail.MuseumObjectApiModel
import com.david.domain.entity.MuseumObject
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

class RemoteObjectDetailDataSourceImplTest {
    private val detailService = mock<DetailService>()
    private val objectDetailDataSource = RemoteObjectDetailDataSourceImpl(
        detailService
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testGetObjectDetail() = runTest {
        val id = 1
        val remoteMuseumObject = MuseumObjectApiModel(1, "", "", listOf(), "Arts", "Dish", "sunflower", "porcelain", "Chelsea", "British")
        val expectedMuseumObject = MuseumObject(1, "", "", listOf(), "Arts", "Dish", "sunflower", "porcelain", "Chelsea", "British")
        whenever(detailService.getObjectDetail(id)).thenReturn(remoteMuseumObject)
        val result = objectDetailDataSource.getObjectDetail(id).first()
        Assert.assertEquals(expectedMuseumObject, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetObjectDetailThrowErrors() = runTest {
        val id = 1
        whenever(detailService.getObjectDetail(id)).thenThrow(RuntimeException())
        objectDetailDataSource.getObjectDetail(id).catch {
            Assert.assertTrue(it is UseCaseException.UnknownException)
        }.collect()
    }
}