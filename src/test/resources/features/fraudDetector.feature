Feature: job reads input files and detects possible frauds

  Scenario: reads with possible frauds are detected
    When the fraud detection job is executed with files "2016-readings.xml 2016-readings.csv"
    Then the output is the same as in "2016-readings-csv-xml-output.txt"

    #TODO:
    #- try not supported extension in input file