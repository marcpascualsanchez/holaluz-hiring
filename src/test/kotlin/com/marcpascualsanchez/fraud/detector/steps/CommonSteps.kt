package com.marcpascualsanchez.fraud.detector.steps

import com.marcpascualsanchez.fraud.detector.BaseApplication
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import java.io.BufferedReader
import java.io.InputStreamReader
import org.assertj.core.api.Assertions.assertThat

@CucumberContextConfiguration
class CommonSteps : BaseApplication() {
    private var output = ""

    @When("the fraud detection job is executed with files {string}")
    fun `the fraud detection job is executed with files`(fileNames: String) {
        output = executeCommand("./gradlew bootRun -P inputFiles=\"$fileNames\"")
        println("START")
        println(output)
        println("END")
    }

    @Then("the output is the same as in {string}")
    fun `the output is the same as in`(fileName: String) {
        assertThat(output).isEqualTo(loadFile("/data/$fileName"))
    }

    private fun loadFile(file: String) =
        this::class.java.getResource(file)!!.readText()

    private fun executeCommand(command: String): String {
        val process = ProcessBuilder(command.split("\\s".toRegex()))
            .start()

        // Read the output of the process
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        var line: String? = reader.readLine()

        while (line != null) {
            println(line)
            line = reader.readLine()
        }

        // Wait for the process to finish
        process.waitFor()

        // Print the output and exit code
        return output
    }
}