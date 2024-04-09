package ch.opentransportdata.ojp.utils

import android.location.Location
import ch.opentransportdata.ojp.data.dto.request.lir.PointDto
import ch.opentransportdata.ojp.data.dto.request.lir.RectangleDto
import ch.opentransportdata.ojp.data.dto.response.GeoPositionDto
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by Vasile on 08.04.2024
 */

internal object GeoLocationUtil {
    fun initWithGeoLocationAndBoxSize(
        longitude: Double,
        latitude: Double,
        boxWidth: Double = 1000.0,
        boxHeight: Double? = null
    ): RectangleDto {
        val geoPosition = GeoPositionDto(longitude, latitude)
        return initWithGeoPositionAndBoxSize(geoPosition, boxWidth, boxHeight)
    }

    private fun initWithGeoPositionAndBoxSize(
        geoPosition: GeoPositionDto,
        boxWidth: Double,
        boxHeightParam: Double? = null
    ): RectangleDto {
        val geoPositionShiftLongitude = GeoPositionDto(geoPosition.longitude!! + 1, geoPosition.latitude)
        val geoPositionShiftLatitude = GeoPositionDto(geoPosition.longitude, geoPosition.latitude!! + 1)

        val longitudeDegreeLength = distanceBetweenGeoPoints(
            point1Latitude = geoPositionShiftLongitude.latitude,
            point1Longitude = geoPositionShiftLongitude.longitude,
            point2Latitude = geoPosition.latitude,
            point2Longitude = geoPosition.longitude
        )
        val latitudeDegreeLength = distanceBetweenGeoPoints(
            point1Latitude = geoPositionShiftLatitude.latitude,
            point1Longitude = geoPositionShiftLatitude.longitude,
            point2Latitude = geoPosition.latitude,
            point2Longitude = geoPosition.longitude
        )

        val deltaLongitude = boxWidth / longitudeDegreeLength
        val deltaLatitude = boxWidth / latitudeDegreeLength

        val boxXMin = roundCoordinate(geoPosition.longitude - deltaLongitude / 2)
        val boxYMin = roundCoordinate(geoPosition.latitude - deltaLatitude / 2)
        val boxXMax = roundCoordinate(geoPosition.longitude + deltaLongitude / 2)
        val boxYMax = roundCoordinate(geoPosition.latitude + deltaLatitude / 2)

        return initWithBBOXCoordinates(boxXMin = boxXMin, boxYMin = boxYMin, boxXMax = boxXMax, boxYMax = boxYMax)
    }

    private fun distanceBetweenGeoPoints(
        point1Latitude: Double?,
        point1Longitude: Double?,
        point2Latitude: Double?,
        point2Longitude: Double?
    ): Double {
        if (point1Latitude == null || point1Longitude == null || point2Latitude == null || point2Longitude == null) return -1.0

        val results = FloatArray(1)
        Location.distanceBetween(
            point1Latitude,
            point1Longitude,
            point2Latitude,
            point2Longitude,
            results
        )
        return results[0].toDouble()
    }

    private fun roundCoordinate(value: Double, scale: Int = 6): Double {
        return BigDecimal(value).setScale(scale, RoundingMode.HALF_EVEN).toDouble()
    }

    private fun initWithBBOXCoordinates(boxXMin: Double, boxYMin: Double, boxXMax: Double, boxYMax: Double): RectangleDto {
        return RectangleDto(
            upperLeft = PointDto(boxXMin.toString(), boxYMax.toString()),
            lowerRight = PointDto(boxXMax.toString(), boxYMin.toString())
        )
    }
}