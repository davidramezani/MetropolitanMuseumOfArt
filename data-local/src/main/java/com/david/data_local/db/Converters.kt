package com.david.data_local.db

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class Converters {

    @TypeConverter
    fun fromListOfStrings(stringList : List<String>) : String {
        return Json.encodeToString(stringList)
    }

    @TypeConverter
    fun toListOfStrings(string : String) : List<String> {
        return Json.decodeFromString(string)
    }
}