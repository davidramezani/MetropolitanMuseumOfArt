package com.david.domain.usecase

import com.david.domain.entity.MuseumObject
import com.david.domain.repository.GetObjectDetailRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever


class GetObjectDetailUseCaseTest {

    private val getObjectDetailRepository = mock(GetObjectDetailRepository::class.java)
    private val getObjectDetailUseCase = GetObjectDetailUseCase(
        mock(UseCase.Configuration::class.java),
        getObjectDetailRepository
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
        whenever(getObjectDetailRepository.getObjectDetail(request.objectId)).thenReturn(
            flowOf(
                museumObject
            )
        )
        val response = getObjectDetailUseCase.process(request).first()
        assertEquals(GetObjectDetailUseCase.Response(museumObject), response)
    }

}