package ch.opentransportdata.ojp.data.dto.converter

import com.tickaroo.tikxml.TypeConverter
import java.time.Duration

/**
 * Created by Michael Ruppen on 31.07.2024
 */
class DurationTypeConverter : TypeConverter<Duration> {
    override fun read(value: String): Duration {
        return Duration.parse(value)
    }

    override fun write(value: Duration): String {
        return value.toString()
    }

}