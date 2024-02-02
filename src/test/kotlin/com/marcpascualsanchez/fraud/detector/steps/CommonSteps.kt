package com.marcpascualsanchez.fraud.detector.steps

import com.marcpascualsanchez.fraud.detector.BaseApplication
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.assertj.core.api.Assertions.assertThat
import java.io.File

@CucumberContextConfiguration
class CommonSteps : BaseApplication() {
    @When("the fraud detection job is executed with files {string}")
    fun `the fraud detection job is executed with files`(fileNames: String) {
        executeCommand(listOf("./gradlew", "bootRun", "-P", "inputFiles=$fileNames"))
    }

    @Then("the output is the same as in {string}")
    fun `the output is the same as in`(fileName: String) {
        assertThat(
            loadFile("src/main/resources/data/output/fraudEvaluation.txt")
        ).isEqualTo(
            loadFile("src/test/resources/data/$fileName")
        )
    }

    private fun loadFile(file: String) = File(file).readText()

    private fun executeCommand(command: List<String>) {
        try {
            val processBuilder = ProcessBuilder(command)

            val process = processBuilder.start()

            process.waitFor()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}