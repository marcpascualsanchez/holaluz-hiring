package com.marcpascualsanchez.fraud.detector.domain.entity

data class FraudEvaluation(
    val clientId: String,
    val period: String,
    val suspiciousReading: Int,
    val median: Double,
)
