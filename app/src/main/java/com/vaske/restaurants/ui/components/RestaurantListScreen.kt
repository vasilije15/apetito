package com.vaske.restaurants.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vaske.restaurants.R
import com.vaske.restaurants.ui.RestaurantsListState
import com.vaske.restaurants.ui.RestaurantsViewModel

@Composable
fun RestaurantListScreen(
    viewModel: RestaurantsViewModel = hiltViewModel(),
    onRestaurantClick: (RestaurantDetailsUi) -> Unit
) {

    val state by viewModel.state.collectAsState()

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        when (state) {
            is RestaurantsListState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is RestaurantsListState.Success -> {
                RestaurantList(
                    restaurantList = (state as RestaurantsListState.Success).listOfRestaurants,
                    onRestaurantClick = {
                        Log.d("Clicked", "Clicked on ${it.name}")
                        onRestaurantClick(it)
                    })
            }
            is RestaurantsListState.Error -> {
                Text(
                    text = (state as RestaurantsListState.Error).reason,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }

            is RestaurantsListState.Empty -> {
                Text(
                    text = stringResource(id = R.string.empty_list_error_message),
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun RestaurantList(
    modifier: Modifier = Modifier,
    restaurantList: List<RestaurantDetailsUi>,
    onRestaurantClick: (RestaurantDetailsUi) -> Unit
) {
    LazyColumn(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(
            items = restaurantList,
            key = { it.uuid }
        ) { restaurant ->
            RestaurantCard(restaurant = restaurant.toSummary(), onClick = { onRestaurantClick(restaurant) })
        }
    }
}
