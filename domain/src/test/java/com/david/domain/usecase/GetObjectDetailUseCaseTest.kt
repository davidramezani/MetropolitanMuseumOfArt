package com.david.domain.usecase

import com.david.domain.entity.MuseumObject
import com.david.domain.repository.GetObjectDetailRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class GetObjectDetailUseCaseTest {

    private val getObjectDetailRepository = mockk<GetObjectDetailRepository>()
    private val getObjectDetailUseCase = GetObjectDetailUseCase(
        mockk(),
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
            objectName = "",
            title = "",
            medium = "",
            artist = "",
            artistBio = ""
        )
        every { getObjectDetailRepository.getObjectDetail(request.objectId) } returns flowOf(
            museumObject
        )
        val response = getObjectDetailUseCase.process(request).first()
        assertEquals(GetObjectDetailUseCase.Response(museumObject), response)
    }

}