package ch.opentransportdata.ojp.domain.model

/**
 * Created by Michael Ruppen on 12.07.2024
 */
data class LocationInformationParams(
    val types: List<PlaceTypeRestriction>,
    val numberOfResults: Int,
    val ptModeIncluded: Boolean
)
