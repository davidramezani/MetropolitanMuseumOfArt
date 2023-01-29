package com.david.data_remote.source

import com.david.data_remote.networking.detail.DetailService
import com.david.data_remote.networking.detail.MuseumObjectApiModel
import com.david.domain.entity.MuseumObject
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

class RemoteObjectDetailDataSourceImplTest {
    private val detailService = mockk<DetailService>()
    private val objectDetailDataSource = RemoteObjectDetailDataSourceImpl(
        detailService
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testGetObjectDetail() = runTest {
        val id = 1
        val remoteMuseumObject = MuseumObjectApiModel(
            1,
            "",
            "",
            listOf(),
            "Arts",
            "Dish",
            "sunflower",
            "porcelain",
            "Chelsea",
            "British"
        )
        val expectedMuseumObject = MuseumObject(
            1,
            "",
            "",
            listOf(),
            "Arts",
            "Dish",
            "sunflower",
            "porcelain",
            "Chelsea",
            "British"
        )
        coEvery { detailService.getObjectDetail(id) } returns (remoteMuseumObject)
        val result = objectDetailDataSource.getObjectDetail(id).first()
        Assert.assertEquals(expectedMuseumObject, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetObjectDetailThrowErrors() = runTest {
        val id = 1
        coEvery { detailService.getObjectDetail(id) } throws (RuntimeException())
        objectDetailDataSource.getObjectDetail(id).catch {
            Assert.assertTrue(it is UseCaseException.UnknownException)
        }.collect()
    }
}