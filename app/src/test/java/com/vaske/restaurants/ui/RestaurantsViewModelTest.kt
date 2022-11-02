@file:OptIn(ExperimentalCoroutinesApi::class)

package com.vaske.restaurants.ui

import com.vaske.restaurants.MainDispatcherRule
import com.vaske.restaurants.MockRestaurantRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RestaurantsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val restaurantsRepository = MockRestaurantRepository()

    private lateinit var restaurantsViewModel: RestaurantsViewModel

    @Before
    fun setup() {
        restaurantsViewModel = RestaurantsViewModel(restaurantsRepository)
    }

    @Test
    fun `state is initially loading`() = runTest {
        assertEquals(RestaurantsListState.Loading, restaurantsViewModel.state.value)
    }

    @Test
    fun `state is empty when there are no restaurants`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { restaurantsViewModel.state.collect() }

       restaurantsRepository.getEmptyResponse()
        assertEquals(RestaurantsListState.Empty, restaurantsViewModel.state.value)

        collectJob.cancel()
    }

    @Test
    fun `state is error when response failed`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { restaurantsViewModel.state.collect() }

        restaurantsRepository.getErrorResponse()
        assertEquals(RestaurantsListState.Error("Well unexpected"), restaurantsViewModel.state.value)

        collectJob.cancel()
    }

    @Test
    fun `state is success after successful response`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { restaurantsViewModel.state.collect() }

        restaurantsRepository.getSuccessResponse()

        assertTrue(restaurantsViewModel.state.value is RestaurantsListState.Success)

        collectJob.cancel()
    }
}
