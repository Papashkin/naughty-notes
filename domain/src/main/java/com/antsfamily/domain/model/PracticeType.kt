package com.antsfamily.domain.model

enum class PracticeType : StatInfo {
    VAGINAL,
    ORAL,
    ANAL,
    MASTURBATION,
    TRIBADISM,
    BDSM,
    THREESOME,
    ;

    val isProtectionNeeded: Boolean
        get() = this != MASTURBATION

    override val isNotUnknown: Boolean
        get() = true
}
