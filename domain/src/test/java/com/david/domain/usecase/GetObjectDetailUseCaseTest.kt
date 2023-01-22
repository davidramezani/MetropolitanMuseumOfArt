package com.david.domain.usecase

import com.david.domain.entity.MuseumObject
import com.david.domain.repository.DetailRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetObjectDetailUseCaseTest {

    private val detailRepository = mock<DetailRepository>()
    private val getObjectDetailUseCase = GetObjectDetailUseCase(
        mock(),
        detailRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runTest {
        val request = GetObjectDetailUseCase.Request(0)
        val museumObject = MuseumObject(
            objectID = 0,
            primaryImage = "",
            primaryImageSmall = "",
            additionalImages = listOf(),
            department = "",
            objectName = ""
        )
        whenever(detailRepository.getObjectDetail(request.objectId)).thenReturn(flowOf(museumObject))
        val response = getObjectDetailUseCase.process(request).first()
        assertEquals(GetObjectDetailUseCase.Response(museumObject), response)
    }

}