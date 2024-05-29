package ch.opentransportdata.ojp.domain.model.error

import com.tickaroo.tikxml.XmlDataException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import java.io.IOException

/**
 * Created by Deniz Kalem on 06.05.2024
 */
sealed class OjpError(open val exception: Exception) {

    // When a response status code is != `200` this error is thrown
    data class UnexpectedHttpStatus(override val exception: HttpException) : OjpError(exception)

    // A response is missing a required element. Eg. no `serviceDelivery` is present
    data class MissingElement(override val exception: NullPointerException) : OjpError(exception)

    // Issue when trying to generate a request xml
    data class EncodingFailed(override val exception: IOException) : OjpError(exception)

    // Can't correctly decode a XML response
    data class DecodingFailed(override val exception: XmlDataException) : OjpError(exception)

    // Job of the coroutine is cancelled while it is suspending
    data class RequestCancelled(override val exception: CancellationException) : OjpError(exception)

    // Unknown error
    data class Unknown(override val exception: Exception) : OjpError(exception)
}