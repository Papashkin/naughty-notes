package com.antsfamily.naughtynotes.presentation.createnote.model

import androidx.annotation.StringRes
import com.antsfamily.domain.model.SexType
import com.antsfamily.naughtynotes.R

@StringRes
fun SexType.toStringId(): Int {
    return when (this) {
        SexType.ANAL -> R.string.sex_type_anal
        SexType.ORAL -> R.string.sex_type_oral
        SexType.VAGINAL -> R.string.sex_type_vaginal
        SexType.MASTURBATION -> R.string.sex_type_masturbation
        SexType.TRIBADISM -> R.string.sex_type_tribadism
        SexType.BDSM -> R.string.sex_type_bdsm
        SexType.UNKNOWN -> R.string.sex_type_unknown
    }
}

@StringRes
fun SexType.toDescriptionStringId(): Int {
    return when (this) {
        SexType.ANAL -> R.string.sex_type_description_anal
        SexType.ORAL -> R.string.sex_type_description_oral
        SexType.VAGINAL -> R.string.sex_type_description_vaginal
        SexType.MASTURBATION -> R.string.sex_type_description_masturbation
        SexType.TRIBADISM -> R.string.sex_type_description_tribadism
        SexType.BDSM -> R.string.sex_type_description_bdsm
        SexType.UNKNOWN -> R.string.sex_type_description_unknown
    }
}