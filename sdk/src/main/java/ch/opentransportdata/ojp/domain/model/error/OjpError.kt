package ch.opentransportdata.ojp.domain.model.error

/**
 * Created by Deniz Kalem on 06.05.2024
 */
enum class OjpError : Error {
    // Used as a placeholder for features, that are not finished implementing
    NOT_IMPLEMENTED,

    // When a response status code is != `200` this error is thrown
    UNEXPECTED_HTTP_STATUS,

    // A response is missing a required element. Eg. no `serviceDelivery` is present
    MISSING_ELEMENT,

    // Issue when trying to generate a request xml
    ENCODING_FAILED,

    // Can't correctly decode a XML response
    DECODING_FAILED,

    // Can't correctly decode a XML response
    REQUEST_CANCELLED,

    // Unknown error
    UNKNOWN
}