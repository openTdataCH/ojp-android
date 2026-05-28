package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Nico Brandenberger on 27.06.2026
 */
@Serializable
enum class StopHierarchy {
    @SerialName("local")
    LOCAL,

    @SerialName("no")
    NO,

    @SerialName("down")
    DOWN,

    @SerialName("all")
    ALL,
}