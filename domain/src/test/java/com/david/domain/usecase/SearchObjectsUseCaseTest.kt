package com.david.domain.usecase

import com.david.domain.repository.SearchRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class SearchObjectsUseCaseTest {

    private val searchRepository = mock<SearchRepository>()
    private val searchObjectsUseCase = SearchObjectsUseCase(
        mock(),
        searchRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runTest {
        val request = SearchObjectsUseCase.Request("sunflower")
        val objectIds = listOf(1, 2, 3)
        whenever(searchRepository.searchObjectIDs(request.searchQuery)).thenReturn(flowOf(objectIds))
        val response = searchObjectsUseCase.process(request).first()
        assertEquals(SearchObjectsUseCase.Response(objectIds), response)
    }

}