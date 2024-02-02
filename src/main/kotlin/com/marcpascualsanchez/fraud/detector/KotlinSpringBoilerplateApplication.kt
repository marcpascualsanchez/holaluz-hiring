package com.marcpascualsanchez.fraud.detector

import com.marcpascualsanchez.fraud.detector.infrastructure.job.DetectFraudJobRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class KotlinSpringBoilerplateApplication(
    private val detectFraudJobRunner: DetectFraudJobRunner
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val inputFilesArg = try {
            args.first()!!
        } catch (e: Exception) {
            println("Argument 'inputFiles' is required")
            throw e
        }
        detectFraudJobRunner.run(inputFilesArg)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(KotlinSpringBoilerplateApplication::class.java, *args)
        }
    }
}