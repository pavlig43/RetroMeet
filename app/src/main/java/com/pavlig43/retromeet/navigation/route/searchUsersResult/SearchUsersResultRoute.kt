package com.pavlig43.retromeet.navigation.route.searchUsersResult

import android.net.Uri
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import com.pavlig43.features.searchuser.SearchUsersScreen
import com.pavlig43.features.searchuser.model.DateOfBirthRangeUi
import com.pavlig43.features.searchuser.model.HeightRangeUi
import com.pavlig43.features.searchuser.model.SearchUserRequestUi
import com.pavlig43.features.searchuser.model.WeightRangeUi
import com.pavlig43.features.searchuser.route.SearchUserRoute
import com.pavlig43.features.searchusersresult.SearchUsersResultScreen
import com.pavlig43.retromeetdata.searchuserRepository.model.WeightRange
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType
import kotlin.reflect.typeOf

fun NavGraphBuilder.searchUsersResult(
    navController: NavHostController
) {
    composable<SearchUserRequestUi>(
        typeMap = mapOf(

            typeMapOf<DateOfBirthRangeUi>(),
            typeMapOf<WeightRangeUi>(),
            typeMapOf<HeightRangeUi>(),
        )
    ) {
        SearchUsersResultScreen()
    }
}
inline fun <reified T> serializableNavType(isNullableAllowed: Boolean = false) =
    object : NavType<T>(isNullableAllowed = isNullableAllowed) {
        override fun put(bundle: Bundle, key: String, value: T) {
            bundle.putString(key, serializeAsValue(value))
        }

        override fun get(bundle: Bundle, key: String): T? {
            return bundle.getString(key)?.let { parseValue(it) }
        }

        override fun serializeAsValue(value: T): String {
            return Json.encodeToString(serializer<T>(),value)
        }

        override fun parseValue(value: String): T {
            return Json.decodeFromString(value)
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is NavType<*>) return false
            if (other::class.java != this::class.java) return false
            if (isNullableAllowed != other.isNullableAllowed) return false
            return true
        }
    }

inline fun <reified T> typeMapOf(): Pair<KType, NavType<T>> {
    val type = typeOf<T>()
    return type to serializableNavType<T>(isNullableAllowed = type.isMarkedNullable)
}
fun main() {
    print(5)
}
