package com.david.metropolitanmuseumofart.presentation_detail

class DetailViewModelTest {

    /*@ExperimentalCoroutinesApi
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @RelaxedMockK
    private lateinit var useCase: GetObjectDetailUseCase

    @RelaxedMockK
    private lateinit var converter: DetailConverter

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
        val uiState = mockk<UiState<DetailModel>>()
        val result = mockk<Result<GetObjectDetailUseCase.Response>>()
        every { useCase.execute(GetObjectDetailUseCase.Request(objectId)) }.returns(flowOf(result))
        every { converter.convert(result) }.returns(uiState)
        viewModel.getMuseumObject()
        assertEquals(uiState, viewModel.museumObjectFlow.value)
    }*/
}