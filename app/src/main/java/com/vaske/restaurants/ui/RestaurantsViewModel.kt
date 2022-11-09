package com.vaske.restaurants.ui

import androidx.lifecycle.*
import com.vaske.restaurants.data.RestaurantsRepository
import com.vaske.restaurants.data.model.CurrentOpenHours
import com.vaske.restaurants.data.model.OpenHours
import com.vaske.restaurants.data.model.Restaurant
import com.vaske.restaurants.ui.components.RestaurantDetailsUi
import com.vaske.restaurants.util.Response
import com.vaske.restaurants.util.randomRestaurantImage
import com.vaske.restaurants.util.parseWorkingTime
import com.vaske.restaurants.util.randomRestaurantEmoji
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    restaurantsRepository: RestaurantsRepository
) : ViewModel() {

    val state: StateFlow<RestaurantsListState> =
        restaurantsRepository.restaurantsResponse.map { response ->
            when (response) {
                is Response.Loading -> RestaurantsListState.Loading
                is Response.Success -> RestaurantsListState.Success(response.data.adaptListForUi())
                is Response.Empty -> RestaurantsListState.Empty
                is Response.Error -> RestaurantsListState.Error(response.message.toString())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RestaurantsListState.Loading
        )

    private fun List<Restaurant>?.adaptListForUi(): List<RestaurantDetailsUi> {
        val listForUi = mutableListOf<RestaurantDetailsUi>()
        this?.forEach {
            listForUi.add(it.adaptForRestaurantCard())
        }
        return listForUi
    }

    private fun Restaurant.adaptForRestaurantCard(): RestaurantDetailsUi =
        RestaurantDetailsUi(
            uuid = this.uid,
            name = this.name + " " + randomRestaurantEmoji(), // should have some logic based on restaurant type to show emoji :D
            type = this.type,
            address = this.address,
            imageUrl = randomRestaurantImage(),
//            imageUrl = this.logo, Uncomment this and comment line above to show diff :(
            openHours = OpenHours(
                current = parseWorkingTime(hours = this.hours) ?: CurrentOpenHours(
                    true,
                    "Not known"
                ),
                weeklyOpenHours = this.hours
            ),
            description = this.description,
            review = this.review,
            phoneNumber = this.phoneNumber
        )
}

sealed interface RestaurantsListState {
    object Loading : RestaurantsListState
    data class Success(val listOfRestaurants: List<RestaurantDetailsUi>) : RestaurantsListState
    data class Error(val reason: String) : RestaurantsListState
    object Empty : RestaurantsListState
}

data class RestaurantSummaryUi(
    val uid: String,
    val name: String,
    val type: String,
    val imageUrl: String,
    val shortAddress: String,
    val currentOpenHours: CurrentOpenHours
)
