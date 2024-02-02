package com.marcpascualsanchez.fraud.detector.infrastructure.file.parser

import com.marcpascualsanchez.fraud.detector.domain.entity.ElectricityRead
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class CSVParser(
    @Value("\${fraud-detector.files.csv.separator}") private val separator: String,
) {
    fun parse(line: String): ElectricityRead? {
        val lineContent = line.split(separator)
        return try {
            ElectricityRead(
                clientId = lineContent[CLIENT_ID_INDEX],
                period = lineContent[PERIOD_INDEX],
                reading = lineContent[READING_INDEX].toInt(),
            )
        } catch (e: Exception) {
            println("Error parsing CSV for line: $line (will be ignored)")
            null
        }
    }

    companion object {
        const val CLIENT_ID_INDEX = 0
        const val PERIOD_INDEX = 1
        const val READING_INDEX = 2
    }
}