package com.pavlig43.retromeetdata.utils

import android.util.Log

@Suppress("TooGenericExceptionCaught")
inline fun <reified T : Enum<T>> enumFromString(name: String?): T? {
    return try {
        name?.let { enumValueOf<T>(it) }
    } catch (e: Exception) {
        Log.d("enumFromString", "$e")
        null
    }
}

@Suppress("TooGenericExceptionCaught")
inline fun <reified T : Enum<T>> listEnumFromString(names: List<String>?): List<T>? {
    return try {
        names?.mapNotNull { enumFromString<T>(it) }
    } catch (e: Exception) {
        Log.d("listEnumFromString", "$e")
        null
    }
}
