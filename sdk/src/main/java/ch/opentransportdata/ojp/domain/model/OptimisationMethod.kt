package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Nico Brandenberger on 19.02.2026
 */
@Serializable
enum class OptimisationMethod() {
    @SerialName("minChanges")
    MIN_CHANGES
}

fun OptimisationMethod.serializedName(): String =
    this::class.java.getField(this.name)
        .getAnnotation(SerialName::class.java)
        ?.value
        ?: this.name