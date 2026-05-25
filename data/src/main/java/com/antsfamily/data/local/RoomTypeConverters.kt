package com.antsfamily.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomTypeConverters {

    @TypeConverter
    fun convertStringListToJSONString(list: List<String>): String =
        Gson().toJson(list)

    @TypeConverter
    fun convertJSONStringToStringList(json: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type)
    }
}
