package com.vaske.restaurants.ui.components

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vaske.restaurants.R
import com.vaske.restaurants.data.model.CurrentOpenHours
import com.vaske.restaurants.ui.RestaurantSummaryUi
import com.vaske.restaurants.ui.components.theme.RestaurantsTheme
import com.vaske.restaurants.util.restaurantDetailDummyData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RestaurantCard(
    modifier: Modifier = Modifier,
    name: String,
    imageUrl: String,
    type: String,
    shortAddress: String,
    currentOpenHours: CurrentOpenHours,
    onClick: () -> Unit = {}
) {

    ElevatedCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImage(
                model = getImageRequest(imageUrl, LocalContext.current),
                contentDescription = "$name image",
                modifier = Modifier
                    .clip(RoundedCornerShape(20))
                    .size(150.dp, 150.dp),
            )
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = name, style = MaterialTheme.typography.titleMedium)
                Text(text = type, style = MaterialTheme.typography.labelMedium)
                Text(text = shortAddress, style = MaterialTheme.typography.labelSmall)
                CurrentOpenHoursText(currentOpenHours = currentOpenHours)
            }
        }
    }
}

@Composable
fun CurrentOpenHoursText(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    currentOpenHours: CurrentOpenHours
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp), modifier = modifier
    ) {
        if (currentOpenHours.isClosed) {
            Text(text = stringResource(id = R.string.closed), color = Color.Red, style = textStyle)
        } else {
            Text(text = stringResource(id = R.string.open), color = Color.Green, style = textStyle)
        }
        Text(text = "○", style = textStyle)
        Text(
            text = if (currentOpenHours.isClosed)
                stringResource(R.string.opens_at, currentOpenHours.at)
            else
                stringResource(R.string.closes_at, currentOpenHours.at),
            style = textStyle,
            textAlign = TextAlign.Center
        )
    }
}

private fun getImageRequest(url: String, context: Context): ImageRequest =
    ImageRequest.Builder(context)
        .data(url)
        .size(500, 500)
        .error(R.drawable.random_restaurant).size(450, 450).build()

@Composable
fun RestaurantCard(
    modifier: Modifier = Modifier,
    restaurant: RestaurantSummaryUi,
    onClick: (RestaurantSummaryUi) -> Unit = {}
) {
    RestaurantCard(
        modifier = modifier,
        name = restaurant.name,
        imageUrl = restaurant.imageUrl,
        type = restaurant.type,
        shortAddress = restaurant.shortAddress,
        currentOpenHours = restaurant.currentOpenHours,
        onClick = { onClick(restaurant) }
    )
}

fun RestaurantDetailsUi.toSummary(): RestaurantSummaryUi =
    RestaurantSummaryUi(
        uid = this.uuid,
        name = this.name,
        type = this.type,
        imageUrl = this.imageUrl,
        shortAddress = this.address.split(",")[0],
        currentOpenHours = this.openHours.current
    )

@Composable
@Preview(showSystemUi = true)
private fun RestaurantCardPreview(
) {
    RestaurantsTheme {
        RestaurantCard(restaurant = restaurantDetailDummyData.toSummary())
    }
}