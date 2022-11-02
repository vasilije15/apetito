package com.vaske.restaurants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vaske.restaurants.ui.MainActivityViewModel
import com.vaske.restaurants.ui.MainScreenState
import com.vaske.restaurants.ui.components.RestaurantDetailsScreen
import com.vaske.restaurants.ui.components.RestaurantListScreen
import com.vaske.restaurants.ui.components.theme.RestaurantsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val mainScreenState by mainViewModel.state.collectAsState()

                    Box(modifier = Modifier.padding(8.dp)) {

                        // TODO: Should implement compose nav instead :/, this is a workaround due to lack of time
                        BackHandler {
                            mainViewModel.showList()
                        }

                        when (mainScreenState) {
                            is MainScreenState.Loading -> CircularProgressIndicator()
                            is MainScreenState.ShowRestaurantDetails ->
                                RestaurantDetailsScreen((mainScreenState as MainScreenState.ShowRestaurantDetails).restaurantDetailsUi)
                            is MainScreenState.List -> RestaurantListScreen(onRestaurantClick = {
                                mainViewModel.setRestaurantDetailsState(it)
                            })
                        }
                    }
                }
            }
        }
    }
}
