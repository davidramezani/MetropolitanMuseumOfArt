package com.david.metropolitanmuseumofart.presentation_search

import androidx.lifecycle.SavedStateHandle
import com.david.domain.entity.SearchResult
import com.david.domain.usecase.GetSearchObjectsUseCase
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
    private lateinit var useCase: GetSearchObjectsUseCase

    private lateinit var viewModel: SearchViewModel

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = createSearchViewModel()
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
        assertEquals(SearchResultUiState.EmptyQuery, viewModel.searchResultUiState.value)
        val searchQuery = "sunflower"
        val uiState = mockk<UiState<SearchedListModel>>()
        val result = mockk<SearchResult>()
        every { useCase(searchQuery) } returns flowOf(result)
        viewModel.onSearchQueryChanged(searchQuery)
        assertEquals(uiState, viewModel.searchResultUiState.value)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearch_ifSearchQueryIsEmpty_thenUseCaseMustNotBeExecuted() {
        assertEquals(SearchResultUiState.EmptyQuery, viewModel.searchResultUiState.value)
        val searchQuery = ""
        viewModel.onSearchQueryChanged(searchQuery)
        verify(exactly = 0) { useCase(searchQuery) }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSearch_ifLengthOfSearchQueryIsLessThanThreeCharacter_thenUseCaseMustNotBeExecuted() {
        assertEquals(SearchResultUiState.EmptyQuery, viewModel.searchResultUiState.value)
        val searchQuery = "as"
        viewModel.onSearchQueryChanged(searchQuery)
        verify(exactly = 0) { useCase(searchQuery) }
    }

    private fun createSearchViewModel() : SearchViewModel {
        val savedStateHandle = SavedStateHandle()
        return SearchViewModel(useCase, savedStateHandle)
    }

}