package ch.opentransportdata.ojp.data.dto.converter

import kotlinx.serialization.KSerializer
import nl.adaptivity.xmlutil.serialization.XML
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class XmlUtilConverterFactory private constructor(
    private val xml: XML
) : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val kSerializer = serializerFor(type)
        return Converter<ResponseBody, Any> { body ->
            xml.decodeFromString(kSerializer as KSerializer<Any>, body.string())
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody> {
        val kSerializer = serializerFor(type)
        val mediaType = "application/xml; charset=UTF-8".toMediaType()
        return Converter<Any, RequestBody> { value ->
            val xmlString = xml.encodeToString(kSerializer as KSerializer<Any>, value)
            xmlString.toRequestBody(mediaType)
        }
    }

    private fun serializerFor(type: Type): KSerializer<*> =
        @Suppress("UNCHECKED_CAST")
        (kotlinx.serialization.serializer(type) as KSerializer<Any>)

    companion object {
        fun create(xml: XML): XmlUtilConverterFactory = XmlUtilConverterFactory(xml)
    }
}