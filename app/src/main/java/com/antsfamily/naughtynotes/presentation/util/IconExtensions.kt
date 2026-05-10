package com.antsfamily.naughtynotes.presentation.util

import androidx.annotation.DrawableRes
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.stats.model.StatInfoType

@DrawableRes
fun StatInfoType.toIconId(): Int =
    when(this) {
        StatInfoType.AVERAGE_RATE -> R.drawable.ic_heart_outlined
        StatInfoType.MOST_ACTIVE_MONTH -> R.drawable.ic_calendar
        StatInfoType.MOST_POPULAR_ACTIVITY -> R.drawable.ic_bolt
        StatInfoType.MOST_POPULAR_LOCATION -> R.drawable.ic_location_wave
    }
