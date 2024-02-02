package com.marcpascualsanchez.fraud.detector.infrastructure.formatter

import com.marcpascualsanchez.fraud.detector.domain.entity.FraudEvaluation
import org.springframework.stereotype.Component

@Component
class TableFormatter {
    val header = "| Client              | Month              | Suspicious         | Median"
    val separator = "\n-------------------------------------------------------------------------------\n"
    fun format(fraudEvaluations: List<FraudEvaluation>): String {
        val table =
            fraudEvaluations.map { "| ${it.clientId}          | ${it.period}            | ${it.suspiciousReading}          | ${it.median}" }
        return header + separator + table.joinToString(separator)
    }

}