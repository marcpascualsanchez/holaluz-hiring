package com.marcpascualsanchez.fraud.detector.infrastructure.formatter

import com.marcpascualsanchez.fraud.detector.domain.entity.FraudEvaluation
import org.springframework.stereotype.Component

@Component
class TableFormatter {
    fun display(fraudEvaluations: List<FraudEvaluation>) {
        println("| Client              | Month              | Suspicious         | Median")
        fraudEvaluations.forEach {
            println(" -------------------------------------------------------------------------------")
            println("| ${it.clientId}          | ${it.period}            | ${it.suspiciousReading}          | ${it.median}")
        }
    }

}