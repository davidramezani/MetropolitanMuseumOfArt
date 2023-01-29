package com.david.metropolitanmuseumofart.presentation_search

import com.david.domain.entity.Result
import com.david.domain.usecase.SearchObjectsUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.UiState
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    private lateinit var useCase: SearchObjectsUseCase

    @RelaxedMockK
    private lateinit var converter: SearchedListConverter
    private lateinit var viewModel: SearchViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchViewModel(useCase, converter)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearch() {
        assertEquals(UiState.Idle, viewModel.searchedListFlow.value)
        val searchQuery = "sunflower"
        val uiState = mockk<UiState<SearchedListModel>>()
        val result = mockk<Result<SearchObjectsUseCase.Response>>()
        every { useCase.execute(SearchObjectsUseCase.Request(searchQuery)) } returns flowOf(result)
        every { converter.convert(result) } returns uiState
        viewModel.search(searchQuery)
        assertEquals(uiState, viewModel.searchedListFlow.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearch_ifSearchQueryIsEmpty_thenUseCaseMustNotBeExecuted() {
        assertEquals(UiState.Idle, viewModel.searchedListFlow.value)
        val searchQuery = ""
        viewModel.search(searchQuery)
        verify(exactly = 0) { useCase.execute(SearchObjectsUseCase.Request(searchQuery)) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearch_ifLengthOfSearchQueryIsLessThanThreeCharacter_thenUseCaseMustNotBeExecuted() {
        assertEquals(UiState.Idle, viewModel.searchedListFlow.value)
        val searchQuery = "as"
        viewModel.search(searchQuery)
        verify(exactly = 0) { useCase.execute(SearchObjectsUseCase.Request(searchQuery)) }
    }

}