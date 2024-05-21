package ch.opentransportdata.ojp

import okio.Buffer
import java.io.File

/**
 * Created by Michael Ruppen on 17.05.2024
 */
internal class TestUtils {

    fun readXmlFile(xmlFile: String): Buffer {
        val xmlData = File(xmlFile).readText()
        return Buffer().writeUtf8(xmlData)
    }
}