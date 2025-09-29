package ch.opentransportdata.ojp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Michael Ruppen on 28.06.2024
 */
@Serializable
enum class ConventionalModesOfOperation {
      @SerialName("scheduled")     SCHEDULED,
      @SerialName("demandResponsive")        DEMAND_RESPONSIVE,
      @SerialName("flexibleRoute")       FLEXIBLE_ROUTE,
      @SerialName("flexibleArea")FLEXIBLE_AREA,
      @SerialName("shuttle")   SHUTTLE,
      @SerialName("pooling")       POOLING,
      @SerialName("replacement") REPLACEMENT,
      @SerialName("school")         SCHOOL,
      @SerialName("pRM")  P_RM,
      @SerialName("unknown")        UNKNOWN
}