package com.vaske.restaurants

import com.vaske.restaurants.data.RestaurantsRepository
import com.vaske.restaurants.data.model.Restaurant
import com.vaske.restaurants.util.Response
import com.vaske.restaurants.util.mockRestaurantList
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow

class MockRestaurantRepository : RestaurantsRepository {

    override val restaurantsResponse: MutableSharedFlow<Response<List<Restaurant>>> =
        MutableSharedFlow(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    suspend fun getEmptyResponse() {
        delay(100)
        restaurantsResponse.tryEmit(Response.Empty())
    }

    suspend fun getErrorResponse() {
        delay(100)
        restaurantsResponse.tryEmit(Response.Error("Well unexpected"))
    }

    suspend fun getSuccessResponse() {
        delay(100)
        restaurantsResponse.tryEmit(Response.Success(mockRestaurantList))
    }

}
