package com.vaske.restaurants.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vaske.restaurants.ui.components.RestaurantDetailsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<MainScreenState>(MainScreenState.Loading)
    val state = _state.asStateFlow()

    init {
        _state.value = MainScreenState.List
    }

    fun setRestaurantDetailsState(restaurantDetailsUi: RestaurantDetailsUi) {
        viewModelScope.launch {
            delay(100)
            _state.value = MainScreenState.ShowRestaurantDetails(restaurantDetailsUi)
        }
    }

    fun showList() {
        viewModelScope.launch {
            _state.value = MainScreenState.List
        }
    }
}

sealed interface MainScreenState {
    object Loading : MainScreenState
    data class ShowRestaurantDetails(val restaurantDetailsUi: RestaurantDetailsUi) : MainScreenState
    object List : MainScreenState
}
