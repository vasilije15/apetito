package com.vaske.restaurants.util

import com.vaske.restaurants.data.model.*
import com.vaske.restaurants.ui.components.RestaurantDetailsUi

// dummy data for preview and tests
val day = Day("9PM", false, "11AM")

val hours = Hours(
    monday = day,
    tuesday = day,
    wednesday = day,
    thursday = day,
    friday = day,
    saturday = day,
    sunday = day,
)

val restaurantDetailDummyData = RestaurantDetailsUi(
    uuid = "123",
    name = "Eat at Vaskes",
    type = "Balkan",
    imageUrl = "www.image.anytime",
    description = "Best pizza in Vaskeland. To achieve and maintain such distinction in food and wine, service, atmosphere and setting that the restaurant gains a first class reputation for gastronomy, gracious and informed hospitality, comfort and beauty which draws new and repeat customers year after year. To achieve the above whilst upholding staff policies and practices which promote a fair and positive working environment.  ",
    review = "Ambience was good, service was no nonsense but friendly.",
    phoneNumber = "123321231132",
    address = "somewhere on earth",
    openHours = OpenHours(
        current = CurrentOpenHours(false, at = "1AM"),
        weeklyOpenHours = hours
    )
)

val mockRestaurantObject = Restaurant(
    id = 123,
    uid ="1251521512",
    name = "Eat at Vaskes",
    type = "Balkan",
    address = "somewhere on earth",
    description = "Best pizza in Vaskeland",
    review = "Ambience was good, service was no nonsense but friendly.",
    logo = "www.image.anytime",
    phoneNumber = "123321231132",
    hours = hours
)

val mockRestaurantList: List<Restaurant> = listOf(mockRestaurantObject)
