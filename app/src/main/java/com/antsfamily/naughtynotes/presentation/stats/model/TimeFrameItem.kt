package com.antsfamily.naughtynotes.presentation.stats.model

enum class TimeFrameItem {
    CURRENT_MONTH,
    PREV_MONTH,
    THIS_YEAR,
    ALL_TIME,
    ;
}

val TIMEFRAME_DEFAULT = TimeFrameItem.CURRENT_MONTH