package com.antsfamily.naughtynotes.presentation.util

import androidx.annotation.StringRes
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
        PracticeType.ANAL -> R.string.sex_type_description_anal
        PracticeType.ORAL -> R.string.sex_type_description_oral
        PracticeType.VAGINAL -> R.string.sex_type_description_vaginal
        PracticeType.MASTURBATION -> R.string.sex_type_description_masturbation
        PracticeType.TRIBADISM -> R.string.sex_type_description_tribadism
        PracticeType.BDSM -> R.string.sex_type_description_bdsm
        PracticeType.THREESOME -> R.string.sex_type_description_threesome
        PracticeType.UNKNOWN -> R.string.sex_type_description_unknown
    }
}

@StringRes
fun NoteFormType.toStringId(): Int = when (this) {
    NoteFormType.CREATE -> R.string.note_form_type_create
    NoteFormType.EDIT -> R.string.note_form_type_edit
}