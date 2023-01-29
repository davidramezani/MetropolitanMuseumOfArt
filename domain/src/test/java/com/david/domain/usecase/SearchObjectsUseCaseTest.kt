package com.david.domain.usecase

import com.david.domain.entity.SearchResult
import com.david.domain.repository.SearchRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


class SearchObjectsUseCaseTest {

    private val searchRepository = mockk<SearchRepository>()
    private val searchObjectsUseCase = SearchObjectsUseCase(
        mockk(),
        searchRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runTest {
        val request = SearchObjectsUseCase.Request("sunflower")
        val searchResult = SearchResult(3, listOf(1, 2, 3))
        every{searchRepository.searchObjectIDs(request.searchQuery)} returns flowOf(searchResult)
        val response = searchObjectsUseCase.process(request).first()
        assertEquals(SearchObjectsUseCase.Response(searchResult), response)
    }

}