package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Nico Brandenberger on 19.02.2026
 */
@Serializable
enum class OptimisationMethod {
    @SerialName("minChanges")
    MIN_CHANGES,
    @SerialName("fastest")
    FASTEST,
}

fun OptimisationMethod.serializedName(): String = when (this) {
    OptimisationMethod.MIN_CHANGES -> "minChanges"
    OptimisationMethod.FASTEST -> "fastest"
}