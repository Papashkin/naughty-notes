package com.antsfamily.naughtynotes.presentation.util

import androidx.annotation.StringRes
import com.antsfamily.domain.model.ErrorType
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinCodeButtonState
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinCodeStep
import com.antsfamily.naughtynotes.presentation.changepincode.model.ChangePinErrorType
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceType
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipType
import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.stats.model.StatInfoType
import com.antsfamily.naughtynotes.presentation.stats.model.TimeFrameItem
import com.antsfamily.naughtynotes.presentation.verifypincode.model.VerificationErrorType

@StringRes
fun PracticeType.toStringId(): Int {
    return when (this) {
        PracticeType.ANAL -> R.string.practice_type_anal
        PracticeType.ORAL -> R.string.practice_type_oral
        PracticeType.VAGINAL -> R.string.practice_type_vaginal
        PracticeType.MASTURBATION -> R.string.practice_type_masturbation
        PracticeType.TRIBADISM -> R.string.practice_type_tribadism
        PracticeType.BDSM -> R.string.practice_type_bdsm
        PracticeType.THREESOME -> R.string.practice_type_threesome
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
    }
}

@StringRes
fun NoteFormType.toStringId(): Int = when (this) {
    NoteFormType.CREATE -> R.string.note_form_type_create
    NoteFormType.EDIT -> R.string.note_form_type_edit
}

@StringRes
fun VerificationErrorType.toStringId(): Int {
    return when (this) {
        VerificationErrorType.FIRST_ATTEMPT -> R.string.pin_code_verification_screen_error_first_attempt
        VerificationErrorType.SECOND_ATTEMPT -> R.string.pin_code_verification_screen_error_second_attempt
        VerificationErrorType.LAST_ATTEMPT -> R.string.pin_code_verification_screen_error_last_attempt
    }
}

@StringRes
fun ChangePinCodeStep.toStringId(): Int {
    return when (this) {
        ChangePinCodeStep.EXISTED_CODE -> R.string.change_existing_pin_code_screen_subtitle_1
        ChangePinCodeStep.NEW_CODE -> R.string.change_existing_pin_code_screen_subtitle_2
        ChangePinCodeStep.REPEAT_NEW_CODE -> R.string.change_existing_pin_code_screen_subtitle_3
    }
}

@StringRes
fun ChangePinCodeButtonState.toStringId(): Int {
    return when (this) {
        ChangePinCodeButtonState.PROCEED -> R.string.change_existing_pin_code_screen_button_continue
        ChangePinCodeButtonState.SAVE -> R.string.change_existing_pin_code_screen_button_save
    }
}

@StringRes
fun ChangePinErrorType.toStringId(): Int {
    return when (this) {
        ChangePinErrorType.WRONG_PIN -> R.string.change_existing_pin_code_screen_error_wrong_pin
        ChangePinErrorType.PINS_NOT_MATCH -> R.string.change_existing_pin_code_screen_error_pins_not_match
        ChangePinErrorType.UNKNOWN -> R.string.change_existing_pin_code_screen_error_unknown
    }
}

@StringRes
fun StatChipType.toStringId(): Int {
    return when (this) {
        StatChipType.ACTIVITY -> R.string.statistic_screen_chip_activity
        StatChipType.PLACE -> R.string.statistic_screen_chip_place
    }
}

@StringRes
fun TimeFrameItem.toStringId(): Int {
    return when (this) {
        TimeFrameItem.CURRENT_MONTH -> R.string.statistic_screen_selector_time_current_month
        TimeFrameItem.PREV_MONTH -> R.string.statistic_screen_selector_time_previous_month
        TimeFrameItem.THIS_YEAR -> R.string.statistic_screen_selector_time_this_year
        TimeFrameItem.ALL_TIME -> R.string.statistic_screen_selector_time_all
    }
}

@StringRes
fun ErrorType.toStringId(): Int {
    return when (this) {
        ErrorType.NETWORK -> R.string.error_screen_message_network
        ErrorType.DATABASE -> R.string.error_screen_message_database
        ErrorType.COMMON -> R.string.error_screen_message_common
        else -> R.string.error_screen_message_unknown
    }
}

@StringRes
fun ChipType.toStringId(): Int =
    when(this) {
        ChipType.PROTECTION -> R.string.note_form_screen_protection_label
        ChipType.ORGASM -> R.string.note_form_screen_your_orgasm_label
        ChipType.PARTNER_ORGASM -> R.string.note_form_screen_partner_orgasm_label
    }

@StringRes
fun StatInfoType.toStringId(): Int =
    when(this) {
        StatInfoType.AVERAGE_RATE -> R.string.statistic_screen_card_average_rate
        StatInfoType.MOST_ACTIVE_MONTH -> R.string.statistic_screen_card_most_active_month
        StatInfoType.MOST_POPULAR_ACTIVITY -> R.string.statistic_screen_card_most_popular_activity
        StatInfoType.MOST_POPULAR_LOCATION -> R.string.statistic_screen_card_most_popular_location
    }
@StringRes
fun ExperienceType.toStringId(): Int =
    when(this) {
        ExperienceType.BAD -> R.string.note_form_screen_experience_bad
        ExperienceType.BELOW_AVERAGE -> R.string.note_form_screen_experience_below_average
        ExperienceType.OKAY -> R.string.note_form_screen_experience_ok
        ExperienceType.GOOD -> R.string.note_form_screen_experience_good
        ExperienceType.AMAZING -> R.string.note_form_screen_experience_amazing
    }