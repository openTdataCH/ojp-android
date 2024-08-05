package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.TransferType
import com.tickaroo.tikxml.TypeConverter

/**
 * Created by Michael Ruppen on 01.07.2024
 */
internal class TransferTypeConverter : TypeConverter<TransferType> {
    override fun read(value: String): TransferType {
        return when (value) {
            "walk" -> TransferType.WALK
            "shuttle" -> TransferType.SHUTTLE
            "taxi" -> TransferType.TAXI
            "protectedConnection" -> TransferType.PROTECTED_CONNECTION
            "guaranteedConnection" -> TransferType.GUARANTEED_CONNECTION
            "remainInVehicle" -> TransferType.REMAIN_IN_VEHICLE
            "changeWithinVehicle" -> TransferType.CHANGE_WITHIN_VEHICLE
            "checkIn" -> TransferType.CHECK_IN
            "checkOut" -> TransferType.CHECK_OUT
            "parkAndRide" -> TransferType.PARK_AND_RIDE
            "bikeAndRide" -> TransferType.BIKE_AND_RIDE
            "carHire" -> TransferType.CAR_HIRE
            "bikeHire" -> TransferType.BIKE_HIRE
            "other" -> TransferType.OTHER
            else -> TransferType.UNKNOWN
        }
    }

    override fun write(value: TransferType): String {
        return when (value) {
            TransferType.WALK -> "walk"
            TransferType.SHUTTLE -> "shuttle"
            TransferType.TAXI -> "taxi"
            TransferType.PROTECTED_CONNECTION -> "protectedConnection"
            TransferType.GUARANTEED_CONNECTION -> "guaranteedConnection"
            TransferType.REMAIN_IN_VEHICLE -> "remainInVehicle"
            TransferType.CHANGE_WITHIN_VEHICLE -> "changeWithinVehicle"
            TransferType.CHECK_IN -> "checkIn"
            TransferType.CHECK_OUT -> "checkOut"
            TransferType.PARK_AND_RIDE -> "parkAndRide"
            TransferType.BIKE_AND_RIDE -> "bikeAndRide"
            TransferType.CAR_HIRE -> "carHire"
            TransferType.BIKE_HIRE -> "bikeHire"
            TransferType.OTHER -> "other"
            else -> "unknown"
        }
    }

}