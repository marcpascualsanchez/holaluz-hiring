package com.marcpascualsanchez.fraud.detector.infrastructure.job

import com.marcpascualsanchez.fraud.detector.application.query.DetectFraudQuery
import com.marcpascualsanchez.fraud.detector.domain.entity.FraudEvaluation
import com.marcpascualsanchez.fraud.detector.infrastructure.formatter.TableFormatter
import com.marcpascualsanchez.fraud.detector.infrastructure.file.service.FileReaderService
import com.marcpascualsanchez.fraud.detector.infrastructure.file.InputFile
import com.marcpascualsanchez.fraud.detector.infrastructure.file.writer.FileWriter
import org.springframework.stereotype.Component

@Component
class DetectFraudJobRunner(

    private val detectFraudQuery: DetectFraudQuery,
    private val fileReaderService: FileReaderService,
    private val tableFormatter: TableFormatter,
    private val fileWriter: FileWriter,
) {
    fun run(fileNamesCI: String) {
        try {
            val files = fileReaderService.parseFileNames(fileNamesCI)
            val outputTable = tableFormatter.format(files.flatMap { readFile(it) })
            fileWriter.write(outputTable)
            println(outputTable)
        } catch (e: Exception) {
            println("Something bad happened: ${e.message}")
            throw e
        }
    }

    private fun readFile(file: InputFile): List<FraudEvaluation> {
        var fileReading = fileReaderService.readFile(file)
        val evaluation = mutableListOf<FraudEvaluation>()
        while (fileReading.isNotEmpty()) {
            evaluation += detectFraudQuery.detectFraud(fileReading)
            fileReading = fileReaderService.readFile(file)
        }
        return evaluation
    }
}