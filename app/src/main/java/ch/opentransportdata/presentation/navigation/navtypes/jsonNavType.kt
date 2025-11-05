package ch.opentransportdata.presentation.navigation.navtypes

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.json.Json

inline fun <reified T> jsonNavType(
    isNullableAllowed: Boolean = false,
    json: Json = Json
) = object : NavType<T>(isNullableAllowed) {
    override fun get(bundle: Bundle, key: String): T? =
        bundle.getString(key)?.let { json.decodeFromString<T>(it) }

    override fun parseValue(value: String): T =
        json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: T): String =
        Uri.encode(json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: T) =
        bundle.putString(key, json.encodeToString(value))
}