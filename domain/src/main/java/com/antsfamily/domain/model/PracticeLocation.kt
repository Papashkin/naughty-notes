package com.antsfamily.domain.model


enum class PracticeLocation: StatInfo {
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
    JACUZZI,
    ROOFTOP,
    AIRPLANE,
    TRAIN,
    OTHER,
    ;

    override val isNotUnknown: Boolean
        get() = true
}
