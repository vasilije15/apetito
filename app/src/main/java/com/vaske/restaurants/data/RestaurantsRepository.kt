package com.vaske.restaurants.data

import com.vaske.restaurants.data.model.Restaurant
import com.vaske.restaurants.util.Response
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RestaurantsRepository {
    val restaurantsResponse: Flow<Response<List<Restaurant>>>
}

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsService: RestaurantsService,
) : RestaurantsRepository {

    override val restaurantsResponse: Flow<Response<List<Restaurant>>> = flow {
        emit(Response.Loading())
        delay(500) // to have some time to show the loading bar :D
        try {
            val response = restaurantsService.getRestaurants()
            if (response.isSuccessful) {
                if (!response.body().isNullOrEmpty()) {
                    emit(Response.Success(response.body()))
                } else {
                    emit(Response.Empty())
                }
            } else {
                emit(Response.Error("Hmmmm, well that's not good. Seems like your request was not successful"))
            }
        } catch (e: Exception) {

            if (e is java.net.UnknownHostException) {
                emit(Response.Error("Seems like we can't reach the global internet \uD83D\uDE2F. Perhaps you are not connected?"))
            } else {
                emit(Response.Error("Well...we all make mistakes sometimes and this time I have not predicted a certain error while loading your content \uD83E\uDD15."))
            }
        }
    }
}
