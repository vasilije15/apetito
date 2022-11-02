package com.vaske.restaurants.data

import com.vaske.restaurants.data.model.Restaurant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantsService {
    @GET("/api/restaurant/random_restaurant")
    suspend fun getRestaurants(@Query("size") size: Int = 7): Response<List<Restaurant>>
}
