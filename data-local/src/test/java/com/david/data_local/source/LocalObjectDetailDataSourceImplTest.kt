package com.david.data_local.source

import com.david.data_local.db.detail.DetailDao
import com.david.data_local.db.detail.MuseumObjectEntity
import com.david.domain.entity.MuseumObject
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class LocalObjectDetailDataSourceImplTest {

    private var detailDao = mockk<DetailDao>()
    private var detailObjectDataSource = LocalObjectDetailDataSourceImpl(
        detailDao
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testGetObjectDetail() = runTest {
        val id = 1
        val museumObjectEntity = MuseumObjectEntity(
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
        every { detailDao.getObjectDetail(id) }.returns(flowOf(museumObjectEntity))
        val result = detailObjectDataSource.getObjectDetail(id).first()
        Assert.assertEquals(expectedMuseumObject, result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddObjectDetail() = runTest {
        val museumObject = MuseumObject(
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
        val museumObjectEntity = MuseumObjectEntity(
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
        detailObjectDataSource.addObjectDetail(museumObject)
        verify(exactly = 1) { detailDao.insertObjectDetail(museumObjectEntity) }
    }

}