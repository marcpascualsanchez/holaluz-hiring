# Comments and thoughts about the code

Hi! This is Marc (your new anti-squatter detective 🥸), I will write down some comments and ideas about the code I'm writing to explain a bit why I take some decisions or another thoughts.

I've tried to keep the project as clean and minimalistic as possible, developing using TDD and applying the DDD & hexagonal patterns using [CQS](https://martinfowler.com/bliki/CommandQuerySeparation.html). This image represents what I try to achieve:
![hexagonalArchitecture.png](assets/hexagonalArchitecture.png)

## Stack
- Kotlin (1.8.22, JDK 17)
- JUnit for unit tests
- E2E tests will be written in Gherkin, using Cucumber. 

Developed using IntelliJ IDEA 2023.3 (Community Edition)

## How to run
```shell
#run project
./gradlew bootRun -P inputFiles="2016-readings.csv 2016-readings.xml"

#run unit tests
./gradlew test

#run e2e tests
./gradlew e2eTest
```
If you want to add more files you have to place them inside the `src/main/resources/data` folder.

## Testing
- I've tried to include e2e tests as a gradle task but I didn't achieve it unfortunately. I run them trough the IntelliJ IDE and they work fine, but not with the command.
- I'm approaching the development by first defining tests and then coding, you will find the features in `resources/features/fraudDetector.feature` and the unit tests in the `src/test` folder.

## Code
- In this case we have only files in local to read. I named the classes that deal with it after that, so they're `readers`. In case this project would handle other sources as databases, I'd rename it to `repository` and adapt it.
- The provided `CSV` and `XML` files are short and I'm reading them whole in memory, but in a production scenario this should not be done since they could be a million time longer, so I've chosen to read them by chunks. 
- Each file is read by 12 lines per client as requested in the instructions, but it should be verified by their client ID. It would add reliability and the possibility to read a file in parallel. I'm also assuming that a client in one file is not in another file and should not be taken into account.
- In order to calculate the median, I will use the average between the two median in case the amount of readings per client are pair (in this case it's everytime since it's 12). Depending on the business rules we could switch to evaluate both medians instead.

## Next Steps
- Add testing centered on errors. Currently this project is lacking a lot of tests about how the system behaves when something fails.
- Add observability, logs + metrics into a system that allows monitoring (IE: Datadog). Here I only used regular CI prints.
- This app could & should be embedded in a docker. I'd create a dockerfile to generate an image with the dependencies needed for the app to run and use that image in a docker-compose file to run it from CI.
- Read different files in parallel.
- Add more e2e test cases (unsupported file extension, wrong file content format...).
- Add test coverage to have visibility on % of tested code.
- Fix the table formatter so it's properly indented.
