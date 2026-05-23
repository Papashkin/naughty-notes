package com.antsfamily.data.local

import androidx.room.TypeConverter
import com.antsfamily.data.model.TypesList
import com.google.gson.Gson

class RoomTypeConverters {

    @TypeConverter
    fun convertStringListToJSONString(list: List<String>): String =
        Gson().toJson(list)

    @TypeConverter
    fun convertJSONStringToInvoiceList(jsonString: String): List<String> =
        Gson().fromJson(jsonString, TypesList::class.java).types
}
