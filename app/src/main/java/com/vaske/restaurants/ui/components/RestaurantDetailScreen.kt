package com.vaske.restaurants.ui.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.vaske.restaurants.R
import com.vaske.restaurants.data.model.*
import com.vaske.restaurants.ui.components.theme.RestaurantsTheme
import com.vaske.restaurants.util.restaurantDetailDummyData

@Composable
fun RestaurantDetailsScreen(
    restaurantDetails: RestaurantDetailsUi, modifier: Modifier = Modifier
) {
    Box(modifier = modifier.verticalScroll(rememberScrollState())) {
        RestaurantDetails(restaurantDetails)
    }
}

@Composable
fun RestaurantDetails(restaurantDetails: RestaurantDetailsUi, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        RestaurantDetails(
            name = restaurantDetails.name,
            type = restaurantDetails.type,
            imageUrl = restaurantDetails.imageUrl,
            description = restaurantDetails.description,
            phoneNumber = restaurantDetails.phoneNumber,
            address = restaurantDetails.address,
            openHours = restaurantDetails.openHours,
            review = restaurantDetails.review
        )
    }
}

@Composable
fun RestaurantDetails(
    name: String,
    type: String,
    imageUrl: String,
    description: String,
    phoneNumber: String,
    address: String,
    openHours: OpenHours,
    review: String
) {

    Column(
        Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)) {
            Text(text = name, style = MaterialTheme.typography.titleLarge)
            Text(text = type, style = MaterialTheme.typography.titleSmall)
            RestaurantImageHeader(imageUrl)
        }
        ExpandableTextRowWithLeadingIcon(icon = Icons.Outlined.Info, text = description) {
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
        IconWithText(
            icon = Icons.Outlined.Place,
            text = address,
            onClick = { Log.d("Click", "Clicked on $address") })
        IconWithText(
            icon = Icons.Outlined.Phone,
            text = phoneNumber,
            onClick = { Log.d("Click", "Clicked on $phoneNumber") })

        ExpandableTextRowWithLeadingIcon(
            icon = Icons.Outlined.Schedule,
            text = null,
            customText = {
                CurrentOpenHoursText(
                    currentOpenHours = openHours.current,
                    textStyle = MaterialTheme.typography.bodyMedium
                )
            },
            expandedContent = {
                RestaurantWeeklyOpenHours(hours = openHours.weeklyOpenHours)
            })

        ExpandableTextRowWithLeadingIcon(
            icon = Icons.Outlined.StarOutline,
            text = stringResource(id = R.string.see_reviews)
        ) {
            Text(text = review, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable // TODO: Not the best approach if day ordering is different, should accept a list of days instead
private fun RestaurantWeeklyOpenHours(hours: Hours, modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            DayRow(dayName = "Sunday", day = hours.sunday)
            DayRow(dayName = "Monday", day = hours.monday)
            DayRow(dayName = "Tuesday", day = hours.tuesday)
            DayRow(dayName = "Wednesday", day = hours.wednesday)
            DayRow(dayName = "Thursday", day = hours.thursday)
            DayRow(dayName = "Friday", day = hours.friday)
            DayRow(dayName = "Saturday", day = hours.sunday)
        }
    }

}

@Composable
private fun DayRow(
    dayName: String,
    day: Day,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = dayName,
            style = textStyle,
            modifier = Modifier.weight(0.4f)
        )
        Text(
            text = day.formatOpenAndClosingHoursForUi(),
            style = textStyle,
            modifier = Modifier.weight(0.4f)
        )
    }
}

fun Day.formatOpenAndClosingHoursForUi(): String = "${this.opensAt} - ${this.closesAt}"

@Composable
fun IconWithText(
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Outlined.Warning,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    onClick: () -> Unit = {},
    showTopDivider: Boolean = true,
    showBottomDivider: Boolean = false
) {

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {

        if (showTopDivider) Divider()

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Row With Icon ${icon.name}",
                modifier = Modifier.weight(0.1f)
            )
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = text, style = textStyle
                )
            }
        }
        if (showBottomDivider) Divider()
    }
}

@Composable
fun ExpandableTextRowWithLeadingIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    expandIcon: ImageVector = Icons.Outlined.KeyboardArrowDown,
    text: String?,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    customText: @Composable RowScope.() -> Unit = {},
    showTopDivider: Boolean = true,
    showBottomDivider: Boolean = false,
    expandedContent: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable { isExpanded = !isExpanded }) {

        if (showTopDivider) Divider()

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Front icon ${icon.name}",
                modifier = Modifier.weight(0.1f)
            )
            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .padding(start = 16.dp)
            ) {
                if (!isExpanded) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Row(modifier = Modifier.weight(0.7f)) {
                            if (text != null) {
                                Text(
                                    text = text,
                                    style = textStyle,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            } else {
                                customText()
                            }
                        }
                        Icon(
                            imageVector = expandIcon,
                            contentDescription = "Expand icon ${expandIcon.name}",
                            modifier = Modifier.weight(0.2f)
                        )
                    }
                } else {
                    expandedContent()
                }
            }
        }
        if (showBottomDivider) Divider()
    }
}

@Composable
private fun RestaurantImageHeader(imageUrl: String) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(8.dp)) {
        AsyncImage(
            model = getImageRequest(imageUrl, LocalContext.current),
            contentDescription = "Restaurant image",
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5)),
            contentScale = ContentScale.FillWidth
        )
    }
}

private fun getImageRequest(url: String, context: Context): ImageRequest =
    ImageRequest.Builder(context).data(url).diskCachePolicy(CachePolicy.ENABLED).crossfade(true)
        .error(R.drawable.random_restaurant)
        .build()

data class RestaurantDetailsUi(
    val uuid: String,
    val name: String,
    val type: String,
    val imageUrl: String,
    val description: String,
    val phoneNumber: String,
    val address: String,
    val openHours: OpenHours,
    val review: String,
)

@Composable
@Preview(showSystemUi = true)
fun RestaurantDetailsPreview() {
    RestaurantsTheme {
        RestaurantDetailsScreen(restaurantDetails = restaurantDetailDummyData)
    }
}
