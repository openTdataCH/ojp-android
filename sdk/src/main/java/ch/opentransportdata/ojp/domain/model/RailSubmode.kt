package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Nico Brandenberger on 19.02.2026
 */
@Serializable
enum class RailSubmode() {
    @SerialName("international") //Trains ICE, TGV, EC, RJX, NJ, EN
    INTERNATIONAL,
    @SerialName("highSpeedRail") //Trains IC
    HIGH_SPEED_RAIL,
    @SerialName("interregionalRail") //Trains IR, IRN, IRE
    INTERREGIONAL_RAIL,
    @SerialName("railShuttle") // Trains ATZ, PE
    RAIL_SHUTTLE,
    @SerialName("local") //Trains S, SN, RB, RE,
    LOCAL,
}

fun RailSubmode.serializedName(): String =
    this::class.java.getField(this.name)
        .getAnnotation(SerialName::class.java)
        ?.value
        ?: this.name