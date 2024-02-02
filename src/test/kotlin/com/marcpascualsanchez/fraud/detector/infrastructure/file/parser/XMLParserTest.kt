package com.marcpascualsanchez.fraud.detector.infrastructure.file.parser

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class XMLParserTest {
    private lateinit var sut: XMLParser

    @BeforeEach
    fun setUp() {
        sut = XMLParser()
    }

    @Test
    fun `given a formatted string parser returns parsed entity`() {
        val response = sut.parse("<reading clientID=\"583ef6329e237\" period=\"2016-02\">31072</reading>")!!

        Assertions.assertThat(response.clientId).isEqualTo("583ef6329e237")
        Assertions.assertThat(response.period).isEqualTo("2016-02")
        Assertions.assertThat(response.reading).isEqualTo(31072)
    }

    @Test
    fun `given an unformatted string parser returns NULL`() {
        val response = sut.parse("583ef6329d81f20160837756")

        Assertions.assertThat(response).isNull()
    }
}