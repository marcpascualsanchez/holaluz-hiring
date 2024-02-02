package com.marcpascualsanchez.fraud.detector.infrastructure.file

data class FilePagination(
    val currentLine: Int,
    val step: Int,
    val headerLines: Int,
) {
        fun nextPage(step: Int): FilePagination =
            this.copy(currentLine = this.currentLine + step)
}
