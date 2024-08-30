package ch.opentransportdata.ojp.domain.model

/**
 * Created by Michael Ruppen on 31.07.2024
 */
data class ModeAndModeOfOperationFilter(
    val ptMode: List<PtMode>,
    val exclude: Boolean
)