package com.marcpascualsanchez.fraud.detector.domain.service

import com.marcpascualsanchez.fraud.detector.domain.entity.ElectricityRead
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FraudEvaluatorServiceTest {
    private lateinit var sut: FraudEvaluatorService

    @BeforeEach
    fun setUp() {
        sut = FraudEvaluatorService()
    }

    @Test
    fun `given impair reads fraud evaluator returns suspiciously high reading`() {
        val clientId = "a1"
        val response = sut.evaluate(
            listOf(
                ElectricityRead(clientId, "2023-01", 2),
                ElectricityRead(clientId, "2023-02", 2),
                ElectricityRead(clientId, "2023-03", 30),
                ElectricityRead(clientId, "2023-04", 2),
                ElectricityRead(clientId, "2023-05", 2),
            )
        )

        Assertions.assertThat(response.size).isEqualTo(1)
        Assertions.assertThat(response.first().clientId).isEqualTo(clientId)
        Assertions.assertThat(response.first().suspiciousReading).isEqualTo(30)
        Assertions.assertThat(response.first().median).isEqualTo(2.0)
    }

    @Test
    fun `given impair reads fraud evaluator returns suspiciously low reading`() {
        val clientId = "a1"
        val response = sut.evaluate(
            listOf(
                ElectricityRead(clientId, "2023-01", 30),
                ElectricityRead(clientId, "2023-02", 30),
                ElectricityRead(clientId, "2023-03", 2),
                ElectricityRead(clientId, "2023-04", 30),
                ElectricityRead(clientId, "2023-05", 30),
            )
        )

        Assertions.assertThat(response.size).isEqualTo(1)
        Assertions.assertThat(response.first().clientId).isEqualTo(clientId)
        Assertions.assertThat(response.first().suspiciousReading).isEqualTo(2)
        Assertions.assertThat(response.first().median).isEqualTo(30.0)
    }

    @Test
    fun `given pair reads fraud evaluator returns suspiciously low reading`() {
        val clientId = "a1"
        val response = sut.evaluate(
            listOf(
                ElectricityRead(clientId, "2023-01", 42451),
                ElectricityRead(clientId, "2023-02", 44279),
                ElectricityRead(clientId, "2023-03", 44055),
                ElectricityRead(clientId, "2023-04", 40953),
                ElectricityRead(clientId, "2023-05", 42566),
                ElectricityRead(clientId, "2023-06", 41216),
                ElectricityRead(clientId, "2023-07", 43597),
                ElectricityRead(clientId, "2023-08", 43324),
                ElectricityRead(clientId, "2023-09", 3564),
                ElectricityRead(clientId, "2023-10", 44459),
                ElectricityRead(clientId, "2023-11", 42997),
                ElectricityRead(clientId, "2023-12", 42600),
            )
        )

        Assertions.assertThat(response.size).isEqualTo(1)
        Assertions.assertThat(response.first().clientId).isEqualTo(clientId)
        Assertions.assertThat(response.first().suspiciousReading).isEqualTo(3564)
        Assertions.assertThat(response.first().median).isEqualTo(42798.5)
    }
}