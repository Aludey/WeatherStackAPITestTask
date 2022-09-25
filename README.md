# WeatherStackAPITestTask

This is a test task that uses Spock Framework to create two types of API Tests that verifying some features from - https://weatherstack.com/

For report generating Allure and Spock Report Extension are used.

## Test run
This project contains two types of tests: <code>PositiveWeatherTests</code> and <code>NegativeAPIErrorsTests</code>

You can also run both test classes simultaneously. For that you should run <code>TestSuiteSpecification</code> class

## Spock Report
To see Spock report after test run you should got to <code>"pathToProject/build/spock-report"</code> and open <code>index.html</code>

## Generate Allure report
In order to generate and open allure report allure should be installed. After that you should run related command in IDE terminal

<code>allure generate allure-results --clean -o allure-report; allure open allure-report</code>
