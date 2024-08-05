package ch.opentransportdata.presentation.navigation.navtypes

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.place.AbstractPlaceDto
import ch.opentransportdata.ojp.data.dto.response.place.AddressDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

/**
 * Created by Michael Ruppen on 05.07.2024
 */

val PlaceResultType = object : NavType<PlaceResultDto?>(isNullableAllowed = true) {
    // needed for abstract classes to correctly resolve polymorphism
    private val json by lazy {
        val module = SerializersModule {
            polymorphic(AbstractPlaceDto::class) {
                subclass(StopPlaceDto::class)
                subclass(AddressDto::class)
            }
        }
        Json { serializersModule = module }
    }

    override fun get(bundle: Bundle, key: String): PlaceResultDto? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, PlaceResultDto::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): PlaceResultDto? {
        return json.decodeFromString<PlaceResultDto?>(value)
    }

    override fun put(bundle: Bundle, key: String, value: PlaceResultDto?) {
        bundle.putParcelable(key, value)
    }

    override fun serializeAsValue(value: PlaceResultDto?): String {
        return json.encodeToString(value)
    }

    override val name: String = PlaceResultDto::class.java.name

}