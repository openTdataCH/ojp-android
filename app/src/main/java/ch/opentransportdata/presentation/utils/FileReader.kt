package ch.opentransportdata.presentation.utils

import android.content.res.Resources
import androidx.annotation.RawRes
import java.io.InputStream

/**
 * Created by Michael Ruppen on 20.09.2024
 */
internal class FileReader {
    fun read(resources: Resources, @RawRes file: Int): InputStream {
        return resources.openRawResource(file)
    }
}