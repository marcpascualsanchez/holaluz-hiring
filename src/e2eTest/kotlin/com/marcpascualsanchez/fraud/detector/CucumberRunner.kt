package com.marcpascualsanchez.fraud.detector

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith

@RunWith(Cucumber::class)
@CucumberOptions(
    stepNotifications = true,
    features = ["src/e2eTest/resources/features"],
    glue = ["com.marcpascualsanchez.fraud.detector.steps"],
    plugin = ["pretty"],
    tags = "not @ignored",
)
class CucumberRunner
