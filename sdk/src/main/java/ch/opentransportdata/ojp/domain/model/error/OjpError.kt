package ch.opentransportdata.ojp.domain.model.error

enum class OjpError : Error {
    // Used as a placeholder for features, that are not finished implementing
    NOT_IMPLEMENTED,

    // A failure occurred, while trying to access the resource
    LOADING_FAILED,

    // When a response status code is != `200` this error is thrown
    UNEXPECTED_HTTP_STATUS,

    // A response is missing a required element. Eg. no `serviceDelivery` is present
    UNEXPECTED_EMPTY,

    // Issue when trying to generate a request xml
    ENCODING_FAILED,

    // Can't correctly decode a XML response
    DECODING_FAILED,

    // Unknown error
    UNKNOWN
}