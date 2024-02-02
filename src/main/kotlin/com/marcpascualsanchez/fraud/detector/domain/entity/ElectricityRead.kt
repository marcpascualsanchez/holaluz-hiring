package com.marcpascualsanchez.fraud.detector.domain.entity

data class ElectricityRead(
    val clientId: String,
    val period: String,
    val reading: Int,
)
