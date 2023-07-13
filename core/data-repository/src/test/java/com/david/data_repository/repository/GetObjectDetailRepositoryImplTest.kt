package com.david.data_repository.repository

import com.david.core.data_repository.data_source.local.LocalObjectDetailDataSource
import com.david.core.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.core.data_repository.repository.GetObjectDetailRepositoryImpl
import com.david.domain.entity.MuseumObject
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

@ExperimentalCoroutinesApi
class GetObjectDetailRepositoryImplTest {

    private val localObjectDetailDataSource = mockk<LocalObjectDetailDataSource>()
    private val remoteObjectDetailDataSource = mockk<RemoteObjectDetailDataSource>()
    private val repositoryImpl = GetObjectDetailRepositoryImpl(
        localObjectDetailDataSource,
        remoteObjectDetailDataSource
    )

    @Test
    fun testGetObjectDetail_ifLocalSourceIsNull_thenFetchFromServerAndAddToDB() = runTest {
        val id = 1
        val museumObject = MuseumObject(
            objectID = id,
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
        every { localObjectDetailDataSource.getObjectDetail(id) }.returns(flowOf(null))
        coEvery { remoteObjectDetailDataSource.getObjectDetail(id) }.returns(museumObject)
        coEvery { localObjectDetailDataSource.addObjectDetail(museumObject) }.returns(Unit)
        repositoryImpl.getObjectDetail(id).collect()
        coVerify(exactly = 1) { remoteObjectDetailDataSource.getObjectDetail(id) }
        coVerify(exactly = 1) { localObjectDetailDataSource.addObjectDetail(museumObject) }
    }

    @Test
    fun testGetObjectDetail_ifLocalSourceIsNotNull_thenReturnLocalData() = runTest {
        val id = 1
        val museumObject = MuseumObject(
            objectID = id,
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
        val result = repositoryImpl.getObjectDetail(id).first()
        coVerify(exactly = 0) { remoteObjectDetailDataSource.getObjectDetail(id) }
        coVerify(exactly = 0) { localObjectDetailDataSource.addObjectDetail(museumObject) }
        Assert.assertEquals(museumObject, result)
    }

}