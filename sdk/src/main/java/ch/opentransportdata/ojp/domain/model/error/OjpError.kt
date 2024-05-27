package ch.opentransportdata.ojp.domain.model.error

/**
 * Created by Deniz Kalem on 06.05.2024
 */
sealed class OjpError : Error {
    // Used as a placeholder for features, that are not finished implementing
    data class NotImplemented(val exception: Exception) : OjpError()

    // When a response status code is != `200` this error is thrown
    data class UnexpectedHttpStatus(val exception: Exception) : OjpError()

    // A response is missing a required element. Eg. no `serviceDelivery` is present
    data class MissingElement(val exception: Exception) : OjpError()

    // Issue when trying to generate a request xml
    data class EncodingFailed(val exception: Exception) : OjpError()

    // Can't correctly decode a XML response
    data class DecodingFailed(val exception: Exception) : OjpError()

    // Can't correctly decode a XML response
    data class RequestCancelled(val exception: Exception) : OjpError()

    // Unknown error
    data class Unknown(val exception: Exception) : OjpError()
}