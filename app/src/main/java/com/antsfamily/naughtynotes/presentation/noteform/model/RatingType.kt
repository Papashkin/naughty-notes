package com.antsfamily.naughtynotes.presentation.noteform.model

import androidx.annotation.DrawableRes
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.model.RatingType.PAIN
import com.antsfamily.naughtynotes.presentation.noteform.model.RatingType.PLEASURE

enum class RatingType {
    PAIN,
    PLEASURE,
    ;
}

@DrawableRes fun RatingType.toSelectedIcon(): Int =
    when(this) {
        PAIN -> R.drawable.ic_broken_heart_filled
        PLEASURE -> R.drawable.ic_heart_filled
    }

@DrawableRes fun RatingType.toDefaultIcon(): Int =
    when(this) {
        PAIN -> R.drawable.ic_broken_heart_outlined
        PLEASURE -> R.drawable.ic_heart_outlined
    }