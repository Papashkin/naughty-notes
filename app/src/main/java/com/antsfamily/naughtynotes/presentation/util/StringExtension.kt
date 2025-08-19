package com.antsfamily.naughtynotes.presentation.util

import androidx.annotation.StringRes
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType

@StringRes
fun PracticeType.toStringId(): Int {
    return when (this) {
        PracticeType.ANAL -> R.string.sex_type_anal
        PracticeType.ORAL -> R.string.sex_type_oral
        PracticeType.VAGINAL -> R.string.sex_type_vaginal
        PracticeType.MASTURBATION -> R.string.sex_type_masturbation
        PracticeType.TRIBADISM -> R.string.sex_type_tribadism
        PracticeType.BDSM -> R.string.sex_type_bdsm
        PracticeType.THREESOME -> R.string.sex_type_threesome
        PracticeType.UNKNOWN -> R.string.sex_type_unknown
    }
}

@StringRes
fun PracticeType.toDescriptionStringId(): Int {
    return when (this) {
        PracticeType.ANAL -> R.string.practice_type_description_anal
        PracticeType.ORAL -> R.string.practice_type_description_oral
        PracticeType.VAGINAL -> R.string.practice_type_description_vaginal
        PracticeType.MASTURBATION -> R.string.practice_type_description_masturbation
        PracticeType.TRIBADISM -> R.string.practice_type_description_tribadism
        PracticeType.BDSM -> R.string.practice_type_description_bdsm
        PracticeType.THREESOME -> R.string.practice_type_description_threesome
        PracticeType.UNKNOWN -> R.string.practice_type_description_unknown
    }
}

@StringRes
fun PracticeLocation.toStringId(): Int {
    return when (this) {
        PracticeLocation.BEDROOM -> R.string.practice_location_bedroom
        PracticeLocation.LIVING_ROOM -> R.string.practice_location_living_room
        PracticeLocation.SHOWER -> R.string.practice_location_shower
        PracticeLocation.KITCHEN -> R.string.practice_location_kitchen
        PracticeLocation.FLOOR -> R.string.practice_location_floor
        PracticeLocation.CAR -> R.string.practice_location_car
        PracticeLocation.HOTEL -> R.string.practice_location_hotel
        PracticeLocation.PUBLIC_RESTROOM -> R.string.practice_location_public_restroom
        PracticeLocation.CHANGING_ROOM -> R.string.practice_location_changing_room
        PracticeLocation.BALCONY -> R.string.practice_location_balcony
        PracticeLocation.BEACH -> R.string.practice_location_beach
        PracticeLocation.FOREST -> R.string.practice_location_forest
        PracticeLocation.TENT -> R.string.practice_location_tent
        PracticeLocation.WORKPLACE -> R.string.practice_location_workspace
        PracticeLocation.ELEVATOR -> R.string.practice_location_lift
        PracticeLocation.SAUNA -> R.string.practice_location_sauna
        PracticeLocation.ROOFTOP -> R.string.practice_location_rooftop
        PracticeLocation.AIRPLANE -> R.string.practice_location_airplane
        PracticeLocation.TRAIN -> R.string.practice_location_train
        PracticeLocation.OTHER -> R.string.practice_location_other
        PracticeLocation.UNKNOWN -> R.string.practice_location_unknown
    }
}

@StringRes
fun NoteFormType.toStringId(): Int = when (this) {
    NoteFormType.CREATE -> R.string.note_form_type_create
    NoteFormType.EDIT -> R.string.note_form_type_edit
}