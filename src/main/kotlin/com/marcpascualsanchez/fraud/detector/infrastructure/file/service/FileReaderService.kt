package com.marcpascualsanchez.fraud.detector.infrastructure.file.service

import com.marcpascualsanchez.fraud.detector.domain.entity.ElectricityRead
import com.marcpascualsanchez.fraud.detector.infrastructure.file.parser.CSVParser
import com.marcpascualsanchez.fraud.detector.infrastructure.file.reader.FileReader
import com.marcpascualsanchez.fraud.detector.infrastructure.file.parser.XMLParser
import com.marcpascualsanchez.fraud.detector.infrastructure.file.FilePagination
import com.marcpascualsanchez.fraud.detector.infrastructure.file.InputFile
import com.marcpascualsanchez.fraud.detector.infrastructure.file.SupportedInputFileExtension
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class FileReaderService(
    @Value("\${fraud-detector.files.lines-by-client}") private val linesByClient: Int,
    private val csvParser: CSVParser,
    private val xmlParser: XMLParser,
    private val fileReader: FileReader,
) {
    fun readFile(file: InputFile): List<ElectricityRead> {
        return when (file.extension) {
            SupportedInputFileExtension.CSV -> {
                fileReader.read(
                    file.fullName,
                    FilePagination(linesByClient, CSV_HEADER_LINES)
                ).mapNotNull { csvParser.parse(it) }
            }

            SupportedInputFileExtension.XML -> {
                fileReader.read(
                    file.fullName,
                    FilePagination(linesByClient, XML_HEADER_LINES)
                ).mapNotNull { xmlParser.parse(it) }
            }
        }
    }

    fun parseFileNames(fileNamesCI: String) = fileNamesCI.split(" ")
        .map {
            InputFile(
                fullName = it,
                extension = SupportedInputFileExtension.from(findExtension(it)),
            )
        }

    private fun findExtension(fileName: String) = fileName.substringAfterLast(".")

    companion object {
        const val CSV_HEADER_LINES = 1
        const val XML_HEADER_LINES = 2
    }
}
