package com.marcpascualsanchez.fraud.detector.application.query

import com.marcpascualsanchez.fraud.detector.domain.entity.ElectricityRead
import com.marcpascualsanchez.fraud.detector.domain.entity.FraudEvaluation
import com.marcpascualsanchez.fraud.detector.domain.service.FraudEvaluatorService
import org.springframework.stereotype.Component

@Component
class DetectFraudQuery(
    private val fraudEvaluatorService: FraudEvaluatorService
) {
    fun detectFraud(reads: List<ElectricityRead>): List<FraudEvaluation> {
        return fraudEvaluatorService.evaluate(reads)
    }
}