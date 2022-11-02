package com.vaske.restaurants.data.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    val id: Int,
    val uid: String,
    val name: String,
    val type: String,
    val description: String,
    val review: String,
    var logo: String,
    @SerializedName("phone_number") val phoneNumber: String,
    val address: String,
    val hours: Hours
)

data class Hours(
    val monday: Day,
    val tuesday: Day,
    val wednesday: Day,
    val thursday: Day,
    val friday: Day,
    val saturday: Day,
    val sunday: Day
)

data class Day(
    @SerializedName("closes_at") val closesAt: String,
    @SerializedName("is_closed") val isClosed: Boolean,
    @SerializedName("opens_at") val opensAt: String
)

data class CurrentOpenHours(val isClosed: Boolean, val at: String)

data class OpenHours(val current: CurrentOpenHours, val weeklyOpenHours: Hours)
