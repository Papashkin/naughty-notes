package com.antsfamily.naughtynotes.presentation.util

import androidx.annotation.StringRes
import com.antsfamily.domain.model.SexType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType

@StringRes
fun SexType.toStringId(): Int {
    return when (this) {
        SexType.ANAL -> R.string.sex_type_anal
        SexType.ORAL -> R.string.sex_type_oral
        SexType.VAGINAL -> R.string.sex_type_vaginal
        SexType.MASTURBATION -> R.string.sex_type_masturbation
        SexType.TRIBADISM -> R.string.sex_type_tribadism
        SexType.BDSM -> R.string.sex_type_bdsm
        SexType.THREESOME -> R.string.sex_type_threesome
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
        SexType.THREESOME -> R.string.sex_type_description_threesome
        SexType.UNKNOWN -> R.string.sex_type_description_unknown
    }
}

@StringRes
fun NoteFormType.toStringId(): Int = when (this) {
    NoteFormType.CREATE -> R.string.note_form_type_create
    NoteFormType.EDIT -> R.string.note_form_type_edit
}