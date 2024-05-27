package ch.opentransportdata.ojp.domain.model.error

import com.tickaroo.tikxml.XmlDataException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Deniz Kalem on 06.05.2024
 */
sealed class OjpError : Error {
    // Used as a placeholder for features, that are not finished implementing
    data class NotImplemented(val exception: Exception) : OjpError()

    // When a response status code is != `200` this error is thrown
    data class UnexpectedHttpStatus(val exception: HttpException) : OjpError()

    // A response is missing a required element. Eg. no `serviceDelivery` is present
    data class MissingElement(val exception: NullPointerException) : OjpError()

    // Issue when trying to generate a request xml
    data class EncodingFailed(val exception: IOException) : OjpError()

    // Can't correctly decode a XML response
    data class DecodingFailed(val exception: XmlDataException) : OjpError()

    // Job of the coroutine is cancelled while it is suspending
    data class RequestCancelled(val exception: CancellationException) : OjpError()

    // Unknown error
    data class Unknown(val exception: Exception) : OjpError()
}