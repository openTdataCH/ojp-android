package ch.opentransportdata.presentation.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class CustomNavType<T : Parcelable>(
    private val clazz: Class<T>,
    private val serializer: KSerializer<T>,
) : NavType<T>(isNullableAllowed = true) {

    override fun get(bundle: Bundle, key: String): T? =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, clazz) as T
        } else {
            @Suppress("DEPRECATION") bundle.getParcelable(key)
        }

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)

    override fun parseValue(value: String): T = Json.decodeFromString(serializer, value)
}