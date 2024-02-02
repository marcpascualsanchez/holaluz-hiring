package com.marcpascualsanchez.fraud.detector.infrastructure.file.parser

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CSVParserTest {
    private lateinit var sut: CSVParser

    @BeforeEach
    fun setUp() {
        sut = CSVParser(",")
    }

    @Test
    fun `given a formatted string parser returns parsed entity`() {
        val response = sut.parse("583ef6329d81f,2016-08,37756")!!

        Assertions.assertThat(response.clientId).isEqualTo("583ef6329d81f")
        Assertions.assertThat(response.period).isEqualTo("2016-08")
        Assertions.assertThat(response.reading).isEqualTo(37756)
    }

    @Test
    fun `given an unformatted string parser returns NULL`() {
        val response = sut.parse("583ef6329d81f2016-0837756")

        Assertions.assertThat(response).isNull()
    }
}