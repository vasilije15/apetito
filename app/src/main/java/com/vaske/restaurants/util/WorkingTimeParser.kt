package com.vaske.restaurants.util

import com.vaske.restaurants.data.model.CurrentOpenHours
import com.vaske.restaurants.data.model.Hours
import java.util.Calendar

// could use LocalDate.now().getDayOfWeek().name() from Java 8 instead

fun parseWorkingTime(today: WeekDay = getToday(), hours: Hours): CurrentOpenHours? {
    when (today) {
        WeekDay.MONDAY -> hours.monday
        WeekDay.TUESDAY -> hours.tuesday
        WeekDay.WEDNESDAY -> hours.wednesday
        WeekDay.THURSDAY -> hours.thursday
        WeekDay.FRIDAY -> hours.friday
        WeekDay.SATURDAY -> hours.saturday
        WeekDay.SUNDAY -> hours.sunday
        else -> null
    }?.let {
        if (it.isClosed) {
            return CurrentOpenHours(true, it.opensAt)
        } else {
            return CurrentOpenHours(false, it.closesAt)
        }
    }
    return null
}

fun getToday(): WeekDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK).let {
    when (it) {
        Calendar.MONDAY -> WeekDay.MONDAY
        Calendar.TUESDAY -> WeekDay.TUESDAY
        Calendar.WEDNESDAY -> WeekDay.WEDNESDAY
        Calendar.THURSDAY -> WeekDay.THURSDAY
        Calendar.FRIDAY -> WeekDay.FRIDAY
        Calendar.SATURDAY -> WeekDay.SATURDAY
        Calendar.SUNDAY -> WeekDay.SUNDAY
        else -> WeekDay.UNKNOWN
    }
}

enum class WeekDay {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, UNKNOWN
}
