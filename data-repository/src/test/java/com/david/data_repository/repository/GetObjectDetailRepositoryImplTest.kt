package com.david.data_repository.repository

import com.david.data_repository.data_source.local.LocalObjectDetailDataSource
import com.david.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class GetObjectDetailRepositoryImplTest {

    private val localObjectDetailDataSource = mockk<LocalObjectDetailDataSource>()
    private val remoteObjectDetailDataSource = mockk<RemoteObjectDetailDataSource>()
    private val repositoryImpl = GetObjectDetailRepositoryImpl(
        localObjectDetailDataSource,
        remoteObjectDetailDataSource
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testGetObjectDetail() = runTest {
        val id = 1
        val museumObject = MuseumObject(
            objectID = 1,
            primaryImage = "",
            primaryImageSmall = "",
            additionalImages = listOf(),
            department = "",
            objectName = "",
            title = "",
            medium = "",
            artist = "",
            artistBio = ""
        )
        every { localObjectDetailDataSource.getObjectDetail(id) }.returns(flowOf(museumObject))
        every { remoteObjectDetailDataSource.getObjectDetail(id) }.returns(flowOf(museumObject))
        coEvery { localObjectDetailDataSource.addObjectDetail(museumObject) } returns Unit
        val result = repositoryImpl.getObjectDetail(id).first()
        Assert.assertEquals(museumObject, result)
        verify(exactly = 1) { remoteObjectDetailDataSource.getObjectDetail(id) }
        coVerify(exactly = 1) { localObjectDetailDataSource.addObjectDetail(museumObject) }
    }

}