package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.RealtimeData
import com.tickaroo.tikxml.TypeConverter


/**
 * Created by Deniz Kalem on 13.08.2024
 */
internal class RealTimeDataConverter : TypeConverter<RealtimeData> {

    override fun read(ojpValue: String): RealtimeData {
        return when (ojpValue) {
            "full" -> RealtimeData.FULL
            "explanatory" -> RealtimeData.EXPLANATORY
            "none" -> RealtimeData.NONE
            else -> RealtimeData.UNKNOWN
        }
    }

    override fun write(type: RealtimeData): String {
        return when (type) {
            RealtimeData.FULL -> "full"
            RealtimeData.EXPLANATORY -> "explanatory"
            RealtimeData.NONE -> "none"
            else -> "unknown"
        }
    }
}
