package com.antsfamily.sexcalendar.presentation.createnote.model

import androidx.annotation.StringRes
import com.antsfamily.sexcalendar.R

enum class SexType {
    UNKNOWN,
    VAGINAL,
    ORAL,
    ANAL,
    MASTURBATION,
    TRIBADISM,
    ;
}


@StringRes
fun SexType.toStringId(): Int {
    return when (this) {
        SexType.ANAL -> R.string.sex_type_anal
        SexType.ORAL -> R.string.sex_type_oral
        SexType.VAGINAL -> R.string.sex_type_vaginal
        SexType.MASTURBATION -> R.string.sex_type_masturbation
        SexType.TRIBADISM -> R.string.sex_type_tribadism
        SexType.UNKNOWN -> R.string.sex_type_unknown
    }
}