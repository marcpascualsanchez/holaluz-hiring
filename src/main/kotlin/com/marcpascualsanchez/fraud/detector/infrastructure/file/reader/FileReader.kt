package com.marcpascualsanchez.fraud.detector.infrastructure.file.reader

import com.marcpascualsanchez.fraud.detector.infrastructure.file.FilePagination
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.FileReader

@Component
class FileReader(
    @Value("\${fraud-detector.files.input-path}") private val inputPath: String
) {
    private lateinit var reader: BufferedReader
    private lateinit var currentFile: String

    fun read(fileName: String, pagination: FilePagination): List<String> {
        initializeReader(fileName, pagination.headerLines)

        return (0 until pagination.step)
            .mapNotNull { reader.readLine() }
    }


    private fun initializeReader(fileName: String, headerLines: Int) {
        if (!::reader.isInitialized || currentFile != fileName) {
            reader = BufferedReader(FileReader("$inputPath/$fileName"))
            ignoreHeader(headerLines)
            currentFile = fileName
        }
    }

    private fun ignoreHeader(lines: Int) = repeat(lines) { reader.readLine() }
}