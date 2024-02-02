package com.marcpascualsanchez.fraud.detector.infrastructure.file.writer

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

@Component
class FileWriter(
    @Value("\${fraud-detector.files.output-file}") private val outputPath: String,
) {
    fun write(content: String) {
        val filePath = Paths.get(outputPath)

        try {
            Files.write(filePath, content.toByteArray(), StandardOpenOption.CREATE, StandardOpenOption.WRITE)
            println("File created at $outputPath")
        } catch (e: Exception) {
            println("An error occurred creating the file at $outputPath: ${e.message}")
        }
    }
}