package com.antsfamily.naughtynotes.presentation.noteform.model.chip

import androidx.annotation.StringRes
import com.antsfamily.naughtynotes.R

enum class ChipType {
    PROTECTION,
    ORGASM,
    PARTNER_ORGASM
}

@StringRes fun ChipType.toStringId(): Int =
    when(this) {
        ChipType.PROTECTION -> R.string.note_form_screen_protection_label
        ChipType.ORGASM -> R.string.note_form_screen_your_orgasm_label
        ChipType.PARTNER_ORGASM -> R.string.note_form_screen_partner_orgasm_label
    }