package ch.opentransportdata.ojp.data.dto.converter

import ch.opentransportdata.ojp.domain.model.FareClass
import com.tickaroo.tikxml.TypeConverter


/**
 * Created by Deniz Kalem on 13.08.2024
 */
internal class FareClassConverter : TypeConverter<FareClass> {

    override fun read(ojpValue: String): FareClass {
        return when (ojpValue.trim()) {
            "unknown" -> FareClass.UNKNOWN
            "firstClass" -> FareClass.FIRST_CLASS
            "secondClass" -> FareClass.SECOND_CLASS
            else -> FareClass.UNKNOWN
        }
    }

    override fun write(type: FareClass): String {
        return when (type) {
            FareClass.UNKNOWN -> "unknown"
            FareClass.FIRST_CLASS -> "firstClass"
            FareClass.SECOND_CLASS -> "secondClass"
        }
    }
}