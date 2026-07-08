package ch.opentransportdata.ojp.domain.model

import ch.opentransportdata.ojp.data.dto.response.place.PointOfInterestDto

/**
 * Created by Deniz Kalem on 02.07.2026
 *
 */
enum class SharingCategory(val value: String) {
    E_SCOOTER("escooter_rental"),
    BIKE("bicycle_rental"),
    CAR("car_sharing"),
    CHARGING_STATION("charging_station");

    companion object {
        fun from(value: String?): SharingCategory? = entries.firstOrNull { it.value == value }
    }
}

val PointOfInterestDto.sharingCategories: List<SharingCategory>
    get() = pointOfInterestCategory
        ?.mapNotNull { SharingCategory.from(it.value) }
        ?: emptyList()