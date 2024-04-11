Feature: job reads input files and detects possible frauds

  Scenario Outline: job reads with possible frauds are detected
    When the fraud detection job is executed with files <inputFiles>
    Then the output is the same as in <outputFile>

    Examples:
      | inputFiles                            | outputFile                         |
      | "2016-readings.xml 2016-readings.csv" | "2016-readings-csv-xml-output.txt" |
      | "2016-readings.csv"                   | "2016-readings-csv-output.txt"     |
      | "2016-readings.xml"                   | "2016-readings-xml-output.txt"     |
