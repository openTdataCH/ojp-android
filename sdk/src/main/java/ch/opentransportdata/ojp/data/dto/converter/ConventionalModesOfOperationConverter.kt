package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.ConventionalModesOfOperation
import com.tickaroo.tikxml.TypeConverter

/**
 * Created by Michael Ruppen on 01.07.2024
 */
internal class ConventionalModesOfOperationConverter : TypeConverter<ConventionalModesOfOperation> {
    override fun read(value: String?): ConventionalModesOfOperation {
        return when (value) {
            "scheduled" -> ConventionalModesOfOperation.SCHEDULED
            "demandResponsive" -> ConventionalModesOfOperation.DEMAND_RESPONSIVE
            "flexibleRoute" -> ConventionalModesOfOperation.FLEXIBLE_ROUTE
            "flexibleArea" -> ConventionalModesOfOperation.FLEXIBLE_AREA
            "shuttle" -> ConventionalModesOfOperation.SHUTTLE
            "pooling" -> ConventionalModesOfOperation.POOLING
            "replacement" -> ConventionalModesOfOperation.REPLACEMENT
            "school" -> ConventionalModesOfOperation.SCHOOL
            "pRM" -> ConventionalModesOfOperation.P_RM
            else -> ConventionalModesOfOperation.UNKNOWN
        }
    }

    override fun write(value: ConventionalModesOfOperation?): String {
        return when (value) {
            ConventionalModesOfOperation.SCHEDULED -> "scheduled"
            ConventionalModesOfOperation.DEMAND_RESPONSIVE -> "demandResponsive"
            ConventionalModesOfOperation.FLEXIBLE_ROUTE -> "flexibleRoute"
            ConventionalModesOfOperation.FLEXIBLE_AREA -> "flexibleArea"
            ConventionalModesOfOperation.SHUTTLE -> "shuttle"
            ConventionalModesOfOperation.POOLING -> "pooling"
            ConventionalModesOfOperation.REPLACEMENT -> "replacement"
            ConventionalModesOfOperation.SCHOOL -> "school"
            ConventionalModesOfOperation.P_RM -> "pRM"
            else -> "unknown"
        }
    }

}