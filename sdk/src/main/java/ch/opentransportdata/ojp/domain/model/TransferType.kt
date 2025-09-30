package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName

/**
 * Created by Michael Ruppen on 28.06.2024
 */
enum class TransferType {
    @SerialName("walk")
    WALK,
    @SerialName("shuttle")
    SHUTTLE,
    @SerialName("taxi")
    TAXI,
    @SerialName("protectedConnection")
    PROTECTED_CONNECTION,
    @SerialName("guaranteedConnection")
    GUARANTEED_CONNECTION,
    @SerialName("remainInVehicle")
    REMAIN_IN_VEHICLE,
    @SerialName("changeWithinVehicle")
    CHANGE_WITHIN_VEHICLE,
    @SerialName("checkIn")
    CHECK_IN,
    @SerialName("checkOut")
    CHECK_OUT,
    @SerialName("parkAndRide")
    PARK_AND_RIDE,
    @SerialName("bikeAndRide")
    BIKE_AND_RIDE,
    @SerialName("carHire")
    CAR_HIRE,
    @SerialName("bikeHire")
    BIKE_HIRE,
    @SerialName("other")
    OTHER,
    @SerialName("unknown")
    UNKNOWN
}