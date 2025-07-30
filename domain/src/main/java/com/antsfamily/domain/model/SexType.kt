package com.antsfamily.domain.model

enum class SexType {
    UNKNOWN,
    VAGINAL,
    ORAL,
    ANAL,
    MASTURBATION,
    TRIBADISM,
    BDSM,
    ;

    val isProtectionNeeded: Boolean
        get() = this != MASTURBATION
}
