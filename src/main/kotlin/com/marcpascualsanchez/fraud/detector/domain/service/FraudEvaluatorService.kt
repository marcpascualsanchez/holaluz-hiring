package com.marcpascualsanchez.fraud.detector.domain.service

import com.marcpascualsanchez.fraud.detector.domain.entity.ElectricityRead
import com.marcpascualsanchez.fraud.detector.domain.entity.FraudEvaluation
import org.springframework.stereotype.Service

@Service
class FraudEvaluatorService {
    fun evaluate(reads: List<ElectricityRead>): List<FraudEvaluation> {
        val median = calculateMedian(reads.map { it.reading })
        return reads.filter { isTooLow(it.reading, median) || isTooHigh(it.reading, median) }
            .map {
                FraudEvaluation(
                    clientId = it.clientId,
                    period = it.period,
                    suspiciousReading = it.reading,
                    median = median,
                )
            }
    }

    private fun calculateMedian(values: List<Int>): Double {
        val sorted = values.sorted()
        val isSizePair = sorted.size % 2 == 0
        val medianIndex = sorted.size.div(2)
        return if (isSizePair) {
            val firstMedian = sorted[medianIndex.minus(1)]
            val secondMedian = sorted[medianIndex]
            firstMedian.plus(secondMedian).div(2.0)
        } else {
            sorted[medianIndex].toDouble()
        }
    }

    private fun isTooLow(read: Int, median: Double) = read < median.times(MIN_MEDIAN_PERCENTAGE)
    private fun isTooHigh(read: Int, median: Double) = read > median.times(MAX_MEDIAN_PERCENTAGE)

    companion object {
        const val MAX_MEDIAN_PERCENTAGE = 1.5
        const val MIN_MEDIAN_PERCENTAGE = 0.5
    }
}