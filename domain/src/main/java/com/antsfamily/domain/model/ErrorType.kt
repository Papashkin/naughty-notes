package com.antsfamily.domain.model

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabaseLockedException
import android.database.sqlite.SQLiteException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

enum class ErrorType {
    NETWORK,
    DATABASE,
    COMMON,
    FLOW_RELATED,
    UNKNOWN,
    ;
}


fun Exception.toType(): ErrorType {
    return when (this) {
        is IOException -> ErrorType.NETWORK

        is SQLiteConstraintException,
        is SQLiteDatabaseLockedException,
        is IllegalArgumentException,
        is SQLiteException -> ErrorType.DATABASE

        is CancellationException -> ErrorType.FLOW_RELATED

        is NoSuchElementException,
        is NullPointerException,
        is IllegalStateException,
        is IndexOutOfBoundsException -> ErrorType.COMMON

        else -> ErrorType.UNKNOWN
    }
}