package com.marcpascualsanchez.fraud.detector.infrastructure.file.reader

import com.marcpascualsanchez.fraud.detector.infrastructure.file.FilePagination
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.FileReader

@Component
class FileReader {
    private lateinit var reader: BufferedReader
    private lateinit var currentFile: String

    fun read(fileName: String, pagination: FilePagination): List<String> {
        initializeReader(fileName, pagination.headerLines)

        return (0 until pagination.step)
            .mapNotNull { reader.readLine() }
    }


    private fun initializeReader(fileName: String, headerLines: Int) {
        if (!::reader.isInitialized || currentFile != fileName) {
            reader = BufferedReader(FileReader("src/main/resources/data/$fileName"))
            ignoreHeader(headerLines)
            currentFile = fileName
        }
    }

    private fun ignoreHeader(lines: Int) = repeat(lines) { reader.readLine() }
}