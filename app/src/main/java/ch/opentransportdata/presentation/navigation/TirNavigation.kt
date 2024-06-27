package ch.opentransportdata.presentation.navigation

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.navigation.NavType
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

/**
 * Created by Michael Ruppen on 24.06.2024
 */
@Serializable
object TripSearchMask

@Serializable
data class TripResults(
    val origin: PlaceResultDto, //todo: Cannot cast origin of type kotlinx.serialization.ContextualSerializer<PlaceResultDto> to a NavType. Make sure to provide custom NavType for this argument.
    val via: PlaceResultDto? = null,
    val destination: PlaceResultDto
)

val TripResultsParametersType = object : NavType<PlaceResultDto>(
    isNullableAllowed = true
) {
    override fun put(bundle: Bundle, key: String, value: PlaceResultDto) {
        bundle.putParcelable(key, value)
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU) //todo: find other solution
    @Suppress("DEPRECATION")
    override fun get(bundle: Bundle, key: String): PlaceResultDto? {
        return bundle.getParcelable(key, PlaceResultDto::class.java)
    }

    override fun parseValue(value: String): PlaceResultDto {
        return Json.decodeFromString<PlaceResultDto>(value)
    }
}




@Serializable
object TripDetails //todo: add trip as param, when models exist