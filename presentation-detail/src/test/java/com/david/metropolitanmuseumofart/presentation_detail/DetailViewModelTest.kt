package com.david.metropolitanmuseumofart.presentation_detail

import androidx.lifecycle.SavedStateHandle
import com.david.domain.entity.Result
import com.david.domain.usecase.GetObjectDetailUseCase
import com.david.metropolitanmuseumofart.presentation_common.state.UiState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    @ExperimentalCoroutinesApi
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    private lateinit var useCase: GetObjectDetailUseCase

    @RelaxedMockK
    private lateinit var converter: MuseumObjectConverter

    private lateinit var viewModel: DetailViewModel

    private val objectId = 1

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        val savedStateHandle = SavedStateHandle()
        savedStateHandle["objectId"] = objectId
        viewModel = DetailViewModel(savedStateHandle, converter, useCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGetMuseumObject() {
        assertEquals(UiState.Loading, viewModel.museumObjectFlow.value)
        val uiState = mockk<UiState<MuseumObjectModel>>()
        val result = mockk<Result<GetObjectDetailUseCase.Response>>()
        every { useCase.execute(GetObjectDetailUseCase.Request(objectId)) }.returns(flowOf(result))
        every { converter.convert(result) }.returns(uiState)
        viewModel.getMuseumObject()
        assertEquals(uiState, viewModel.museumObjectFlow.value)
    }
}