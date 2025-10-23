package com.antsfamily.domain.model

enum class PracticeType : StatInfo {
    UNKNOWN,
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
}
