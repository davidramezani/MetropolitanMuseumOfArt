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


class GetSearchObjectsUseCaseTest {

    private val searchQuery = "sunflower"
    private val searchRepository = mockk<SearchRepository>()
    private val getSearchObjectsUseCase = GetSearchObjectsUseCase(
        searchRepository
    )

    @ExperimentalCoroutinesApi
    @Test
    fun testProcess() = runTest {
        val searchResult = SearchResult(3, listOf(1, 2, 3))
        every{searchRepository.searchObjectIDs(searchQuery)} returns flowOf(searchResult)
        val response = getSearchObjectsUseCase(searchQuery).first()
        assertEquals(searchResult, response)
    }

}