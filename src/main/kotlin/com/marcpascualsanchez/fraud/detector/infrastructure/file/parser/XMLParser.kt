package com.marcpascualsanchez.fraud.detector.infrastructure.file.parser

import com.marcpascualsanchez.fraud.detector.domain.entity.ElectricityRead
import org.springframework.stereotype.Component
import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory


@Component
class XMLParser {
    private final val factory = DocumentBuilderFactory.newInstance()
    private final val builder = factory.newDocumentBuilder()

    fun parse(line: String): ElectricityRead? {
        return try {
            mapToElectricityRead(parseXML(line))
        } catch (e: Exception) {
            println("Error parsing XML for line: $line (will be ignored)")
            null
        }
    }

    private fun parseXML(inputDocument: String): Document = builder.parse(InputSource(StringReader(inputDocument)))

    private fun mapToElectricityRead(document: Document) =
        ElectricityRead(
            clientId = document.documentElement.getAttribute(CLIENT_ID_ATTRIBUTE),
            period = document.documentElement.getAttribute(PERIOD_ATTRIBUTE),
            reading = document.documentElement.textContent.toInt(),
        )

    companion object {
        const val CLIENT_ID_ATTRIBUTE = "clientID"
        const val PERIOD_ATTRIBUTE = "period"
    }
}