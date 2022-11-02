package com.vaske.restaurants.di

import com.vaske.restaurants.data.RestaurantsRepository
import com.vaske.restaurants.data.RestaurantsRepositoryImpl
import com.vaske.restaurants.data.RestaurantsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL: String = "https://random-data-api.com/"

    @Provides
    @Singleton
    fun provideRandomDataApi(): RestaurantsService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestaurantsService::class.java)
    }

    @Provides
    @Singleton
    fun provideRestaurantRepository(api: RestaurantsService): RestaurantsRepository {
        return RestaurantsRepositoryImpl(api)
    }
}
