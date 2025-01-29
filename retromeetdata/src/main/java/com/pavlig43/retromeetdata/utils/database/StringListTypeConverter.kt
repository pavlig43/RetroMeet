package com.pavlig43.retromeetdata.utils.database

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.let

class StringListTypeConverter {

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return value?.let { Json.encodeToString(ListSerializer(String.serializer()), it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(it) }
    }
}
