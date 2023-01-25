package com.david.data_repository.repository

import com.david.data_repository.data_source.local.LocalObjectDetailDataSource
import com.david.data_repository.data_source.remote.RemoteObjectDetailDataSource
import com.david.domain.entity.MuseumObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetObjectDetailRepositoryImplTest {

    private val localObjectDetailDataSource = mock<LocalObjectDetailDataSource>()
    private val remoteObjectDetailDataSource = mock<RemoteObjectDetailDataSource>()
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
            objectName = ""
        )
        whenever(localObjectDetailDataSource.getObjectDetail(id)).thenReturn(flowOf(museumObject))
        val result = repositoryImpl.getObjectDetail(id).first()
        Assert.assertEquals(museumObject, result)
        verify(remoteObjectDetailDataSource).getObjectDetail(id)
    }

}