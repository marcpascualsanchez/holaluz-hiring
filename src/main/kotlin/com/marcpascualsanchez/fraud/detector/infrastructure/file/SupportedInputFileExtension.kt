package com.marcpascualsanchez.fraud.detector.infrastructure.file

import com.marcpascualsanchez.fraud.detector.infrastructure.exception.InputFileExtensionNotSupportedException

enum class SupportedInputFileExtension {
    CSV, XML;
    companion object {
        fun from(extension: String): SupportedInputFileExtension {
            try {
                return values().first { it.name == extension.uppercase() }
            } catch (e: Exception) {
                println("extension not valid: $extension")
                throw InputFileExtensionNotSupportedException(extension)
            }
        }
    }
}