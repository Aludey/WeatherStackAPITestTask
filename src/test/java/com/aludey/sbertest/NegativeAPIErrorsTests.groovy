package com.aludey.sbertest

import spock.lang.Unroll

import static io.qameta.allure.Allure.step

class NegativeAPIErrorsTests extends BaseAPITest {

    @Unroll("Check api for Error #expecetedCode")
    def apiErrorTest() {

        when:
        step "Make incorrect request for #error Error"
        def response = method

        then:
        verifyAll {
            step "Success should be false"
            def success = findInString(response, "success")
            success == "false"

            step "Error code should be correct"
            def errorInfo = findInString(response, "error")
            def errorCode = findInString(errorInfo, "code")
            errorCode == expecetedCode

            step "Error type should be correct"
            def errorType = findInString(errorInfo, "type")
            errorType == expectedType

            step("Error description should be correct")
            def errorDescription = findInString(errorInfo, "info")
            errorDescription == expectedDescription
        }

        where:
        expecetedCode | method                                        | expectedType                 | expectedDescription
        "101"         | makeRequestWithoutAccessKey()                 | "missing_access_key"         | "You have not supplied an API Access Key. [Required format: access_key=YOUR_ACCESS_KEY]"
        "101"         | makeRequestWithInvalidAccessKey()             | "invalid_access_key"         | "You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]"
        "103"         | makeRequestWithInvalidAPIFunction()           | "invalid_api_function"       | "This API Function does not exist."
        "105"         | makeCurrentRequestWithLanguageParameter("ru") | "function_access_restricted" | "Access Restricted - Your current Subscription Plan does not support this API Function."
        "601"         | makeRequestWOQuery()                          | "missing_query"              | "Please specify a valid location identifier using the query parameter."
        "606"         | makeCurrentRequestWithUnitParameter("x")      | "invalid_unit"               | "You have specified an invalid unit. Please try again or refer to our API documentation."
    }


    def makeRequestWithoutAccessKey() {
        URL url = new URL(baseURL + "current?access_key=")
        return requestWithOnlyURL(url)
    }

    def makeRequestWithInvalidAccessKey() {
        URL url = new URL(baseURL + "current?access_key=d6cc6c389390088")
        return requestWithOnlyURL(url)
    }

    def makeRequestWithInvalidAPIFunction() {
        return requestWithFunctionAndParameters("maketea", "&query=with lemon")
    }

    def makeRequestWOQuery() {
        return requestWithFunctionAndParameters("current", "&query=")
    }

    def makeCurrentRequestWithLanguageParameter(String language) {
        return requestWithFunctionAndParameters("current", "&query=New York&language=" + language)
    }

    def makeCurrentRequestWithUnitParameter(String unit) {
        return requestWithFunctionAndParameters("current", "&query=New York&units=" + unit)
    }
}
