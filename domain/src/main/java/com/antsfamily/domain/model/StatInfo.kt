package com.antsfamily.domain.model

interface StatInfo {
    val isNotUnknown: Boolean
}

object Other: StatInfo {
    override val isNotUnknown: Boolean
        get() = false

}