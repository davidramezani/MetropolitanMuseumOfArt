package com.david.data_local.source

import com.david.data_local.db.detail.DetailDao
import com.david.data_local.db.detail.MuseumObjectEntity
import com.david.domain.entity.MuseumObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class LocalObjectDetailDataSourceImplTest {

    private var detailDao = mock<DetailDao>()
    private var detailObjectDataSource = LocalObjectDetailDataSourceImpl(
        detailDao
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testGetObjectDetail() = runTest {
        val id = 1
        val museumObjectEntity = MuseumObjectEntity(1, "", "", listOf(), "Arts", "Dish", "sunflower", "porcelain", "Chelsea", "British")
        val expectedMuseumObject = MuseumObject(1, "", "", listOf(), "Arts", "Dish", "sunflower", "porcelain", "Chelsea", "British")
        whenever(detailDao.getObjectDetail(id)).thenReturn(flowOf(museumObjectEntity))
        val result = detailObjectDataSource.getObjectDetail(id).first()
        Assert.assertEquals(expectedMuseumObject, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddObjectDetail() = runTest {
        val museumObject = MuseumObject(1, "", "", listOf(), "Arts", "Dish", "sunflower", "porcelain", "Chelsea", "British")
        val museumObjectEntity = MuseumObjectEntity(1, "", "", listOf(), "Arts", "Dish", "sunflower", "porcelain", "Chelsea", "British")
        detailObjectDataSource.addObjectDetail(museumObject)
        verify(detailDao).insertObjectDetail(museumObjectEntity)
    }

}