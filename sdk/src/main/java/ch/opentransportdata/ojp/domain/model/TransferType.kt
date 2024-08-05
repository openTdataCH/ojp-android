package ch.opentransportdata.ojp.domain.model

/**
 * Created by Michael Ruppen on 28.06.2024
 */
enum class TransferType {
    WALK,
    SHUTTLE,
    TAXI,
    PROTECTED_CONNECTION,
    GUARANTEED_CONNECTION,
    REMAIN_IN_VEHICLE,
    CHANGE_WITHIN_VEHICLE,
    CHECK_IN,
    CHECK_OUT,
    PARK_AND_RIDE,
    BIKE_AND_RIDE,
    CAR_HIRE,
    BIKE_HIRE,
    OTHER,
    UNKNOWN
}