package com.antsfamily.domain.model


enum class PracticeLocation: StatInfo {
    UNKNOWN,
    BEDROOM,
    LIVING_ROOM,
    SHOWER,
    KITCHEN,
    FLOOR,
    CAR,
    HOTEL,
    PUBLIC_RESTROOM,
    CHANGING_ROOM,
    BALCONY,
    BEACH,
    FOREST,
    TENT,
    WORKPLACE,
    ELEVATOR,
    SAUNA,
    ROOFTOP,
    AIRPLANE,
    TRAIN,
    OTHER,
    ;

    override val isNotUnknown: Boolean
        get() = this != UNKNOWN
}
