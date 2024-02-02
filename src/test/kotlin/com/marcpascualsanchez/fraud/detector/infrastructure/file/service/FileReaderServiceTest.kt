package com.marcpascualsanchez.fraud.detector.infrastructure.file.service

import com.marcpascualsanchez.fraud.detector.infrastructure.file.SupportedInputFileExtension
import com.marcpascualsanchez.fraud.detector.infrastructure.file.parser.CSVParser
import com.marcpascualsanchez.fraud.detector.infrastructure.file.parser.XMLParser
import com.marcpascualsanchez.fraud.detector.infrastructure.file.reader.FileReader
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FileReaderServiceTest {
    private lateinit var sut: FileReaderService

    @MockK
    lateinit var csvParser: CSVParser

    @MockK
    lateinit var xmlParser: XMLParser

    @MockK
    lateinit var fileReader: FileReader

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        sut = FileReaderService(
            12,
            csvParser,
            xmlParser,
            fileReader,
        )
    }

    @Test
    fun `CSV file name is parsed correctly`() {
        val response = sut.parseFileNames("2016-readings.csv")

        Assertions.assertThat(response.size).isEqualTo(1)
        Assertions.assertThat(response.first().fullName).isEqualTo("2016-readings.csv")
        Assertions.assertThat(response.first().extension).isEqualTo(SupportedInputFileExtension.CSV)
    }

    @Test
    fun `XML file name is parsed correctly`() {
        val response = sut.parseFileNames("2016-readings.xml")

        Assertions.assertThat(response.size).isEqualTo(1)
        Assertions.assertThat(response.first().fullName).isEqualTo("2016-readings.xml")
        Assertions.assertThat(response.first().extension).isEqualTo(SupportedInputFileExtension.XML)
    }
}